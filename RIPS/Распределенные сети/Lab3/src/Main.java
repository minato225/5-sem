import text.entities.Punctuation;
import text.entities.Text;
import text.parser.*;
import text.parser.handlers.*;
import utilities.Extensions;
import utilities.FileHelper;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        var fileHelper = new FileHelper("SomeRandomText.txt");
        var parserBuilder = Extensions.ParserChainBuilder(new TextBlockHandler(), new SentenceHandler(),
                new WordHandler(), new PunctuationHandler());

        var parser = new TextParser(parserBuilder);
        var plainText = fileHelper.getTextFromFile();
        Text text = parser.parse(plainText);
        System.out.println("------------------------------TEXT---------------------------");
        System.out.println(text);
        System.out.println("--------------------------Words Replace----------------------");
        System.out.println(Extensions.replace(text, "######", 5));
        System.out.println("---------------------------First letter----------------------");
        System.out.println(Extensions.wordsChanges(text, true));

        System.out.println("-------------------------Punctuation-------------------------");
        text.getComponents().stream()
                .filter(x -> x instanceof Punctuation)
                .forEach(System.out::println);

        System.out.println("---------------------------Sentences-------------------------");
        parserBuilder = Extensions.ParserChainBuilder(new TextBlockHandler());
        parser = new TextParser(parserBuilder);
        text = parser.parse(plainText);
        System.out.println(text);

        System.out.println("---------------------------TextBlock-------------------------");
        parserBuilder = Extensions.ParserChainBuilder(new SentenceHandler());
        parser = new TextParser(parserBuilder);
        text = parser.parse(plainText);
        System.out.println(text);

        System.out.println(Extensions.UseLocalization("rus"));
    }
}