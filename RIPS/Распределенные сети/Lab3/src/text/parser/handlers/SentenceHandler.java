package text.parser.handlers;

import text.entities.*;

import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SentenceHandler extends TextHandlerBase {

    public List<TextComponent> Handle(TextComponent request) {
        if (request instanceof Sentence p)
            return this.addSentence(p.getContent());
        else
            return super.Handle(request);
    }

    private List<TextComponent> addSentence(String request) {
        var pattern = "(\\p{Punct})|(\\w+)";
        var p = "\\p{Punct}";
        return Pattern.compile(pattern)
                .matcher(request).results()
                .map(MatchResult::group)
                .flatMap(x -> super.Handle(x.matches(p) ? new Punctuation(x) : new Word(x)).stream())
                .collect(Collectors.toList());
    }
}