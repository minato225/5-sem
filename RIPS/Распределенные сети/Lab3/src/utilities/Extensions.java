package utilities;

import text.entities.Text;
import text.entities.Word;
import text.parser.handlers.*;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Extensions {

    private static final Logger logger = LogManager.getLogger();

    public static ITextHandler ParserChainBuilder(ITextHandler... params) {
        logger.info("Create new Parsers chain links.");
        for (int i = 0; i < params.length - 1; i++){

                logger.error("parser is null!!!");



            params[i].SetNext(params[i + 1]);
        }

        return params[0];
    }

    /**
     * Replace all length sized words in sentence by new word.
     *
     * @param text   some text to change.
     * @param word   some word.
     * @param length length of word for replace.
     * @return new Sentence.
     */
    public static Text replace(Text text, String word, int length) {
        logger.info("Replace start...");
        if (length < 0)
            logger.error("length is below zero.");

        return new Text(text.getComponents().stream()
                .map(x -> x instanceof Word w && w.getContent().length() == length ? new Word(word) : x)
                .collect(Collectors.toList()));
    }

    /**
     * Modifies each word in the text,
     * removing from it all subsequent (previous) occurrences
     * of the first (last) letter of this word.
     *
     * @param text some text.
     * @return new text.
     */
    public static Text wordsChanges(Text text, boolean first) {
        return new Text(text.getComponents().stream()
                .map(w -> {
                    if (w instanceof Word wordContent && wordContent.getContent().length() > 1) {
                        var word = wordContent.getContent();
                        var length = word.length();
                        var ch = "" + (first ? word.charAt(0) : word.charAt(length - 1));
                        return new Word(word.replaceAll(ch, "_"));
                    }
                    return w;
                }).collect(Collectors.toList()));
    }

    public static String UseLocalization(String language) {
        var locale = new Locale(language);
        var messages = ResourceBundle.getBundle("resourceBundle/localisation", locale);
        var hi = messages.getString("hi");
        var buy = messages.getString("buy");
        var cat = messages.getString("cat");

        return "{%s} - {%s} - {%s}".formatted(hi, buy, cat);
    }
}
