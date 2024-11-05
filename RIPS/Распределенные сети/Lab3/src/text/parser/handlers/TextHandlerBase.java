package text.parser.handlers;

import text.entities.TextComponent;

import java.util.List;

public abstract class TextHandlerBase implements ITextHandler {
    public ITextHandler nextHandler;

    @Override
    public void SetNext(ITextHandler handler) {
        this.nextHandler = handler;
    }

    @Override
    public List<TextComponent> Handle(TextComponent request) {
        return this.nextHandler != null ? this.nextHandler.Handle(request) : List.of(request);
    }
}
