package text.entities;

public class TextBlock extends TextComponent {

    public TextBlock(String content) throws IllegalArgumentException {
        super(content);
    }

    @Override
    protected String name() {
        return "TextBlock";
    }

    @Override
    protected boolean isTextValid(String s) {
        return s.chars().filter(x -> x == '\n').count() >=2;
    }
}
