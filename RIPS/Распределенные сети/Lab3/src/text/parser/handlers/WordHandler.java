package text.parser.handlers;

import text.entities.TextComponent;
import text.entities.Word;

import java.util.List;

public class WordHandler extends TextHandlerBase {

    public List<TextComponent> Handle(TextComponent request) {
        if (request instanceof Word word)
            return this.getSentence(word.getContent());
        else
            return super.Handle(request);
    }

    private List<TextComponent> getSentence(String request) {
        return List.of(new Word(request));
    }
}
