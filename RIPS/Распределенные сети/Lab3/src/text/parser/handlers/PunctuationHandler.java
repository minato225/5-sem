package text.parser.handlers;

import text.entities.Punctuation;
import text.entities.TextComponent;
import java.util.List;

public class PunctuationHandler extends TextHandlerBase {

    public List<TextComponent> Handle(TextComponent request) {
        if (request instanceof Punctuation p)
            return this.addPunctuation(p.getContent());
        else
            return super.Handle(request);
    }

    private List<TextComponent> addPunctuation(String request) {
        return List.of(new Punctuation(request));
    }
}
