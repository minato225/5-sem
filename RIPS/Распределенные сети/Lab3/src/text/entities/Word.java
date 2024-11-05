package text.entities;

public class Word extends TextComponent {
    public Word(String word) {
        super(word);
    }

    @Override
    protected boolean isTextValid(String s) {
        return !s.contains("\\s|\\p{Punct}");
    }

    @Override
    protected String name() {
        return "Word";
    }
}
