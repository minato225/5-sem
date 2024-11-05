package text.entities;

public class Punctuation extends TextComponent {
    public Punctuation(String content) throws IllegalArgumentException {
        super(content);
    }

    @Override
    protected String name() {
        return "Punctuation";
    }

    @Override
    protected boolean isTextValid(String s) {
        return s.matches("\\p{Punct}");
    }
}
