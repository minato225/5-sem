package text.parser.handlers;

import text.entities.Sentence;
import text.entities.TextBlock;
import text.entities.TextComponent;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TextBlockHandler extends TextHandlerBase {

    public List<TextComponent> Handle(TextComponent request) {
        if (request instanceof TextBlock p)
            return this.addTextBlocks(p.getContent());
        else
            return super.Handle(request);
    }

    private List<TextComponent> addTextBlocks(String request) {
        return Pattern.compile("([^.!?]+[.!?])")
                .matcher(request).results()
                .map(MatchResult::group)
                .flatMap(x -> super.Handle(new Sentence(x)).stream())
                .collect(Collectors.toList());
    }
}