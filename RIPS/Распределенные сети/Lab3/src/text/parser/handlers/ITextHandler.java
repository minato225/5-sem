package text.parser.handlers;

import text.entities.TextComponent;

import java.util.List;

public interface ITextHandler {
    void SetNext(ITextHandler handler);
    List<TextComponent> Handle(TextComponent request);
}
