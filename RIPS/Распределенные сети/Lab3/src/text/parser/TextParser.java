package text.parser;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import text.entities.Text;
import text.entities.TextBlock;
import text.parser.handlers.ITextHandler;

public record TextParser(ITextHandler parserChain) {

    private final static Logger logger = LogManager.getLogger();

    public Text parse(String plainText) {
        var contents = normalizeTextSpaces(plainText).split("<paragraph>");
        logger.info("Text parser start.");

        return new Text(Arrays.stream(contents)
                .flatMap(x -> parserChain.Handle(new TextBlock(x)).stream())
                .collect(Collectors.toList()));
    }

    private String normalizeTextSpaces(String text) {
        return text.trim().replaceAll("\\s{2,}", " ");
    }
}
