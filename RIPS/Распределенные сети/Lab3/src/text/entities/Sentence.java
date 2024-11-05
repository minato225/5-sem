package text.entities;

public class Sentence extends TextComponent {
    public Sentence(String textData) {
        super(textData);
    }

    @Override
    protected String name() {
        return "Sentence";
    }

    @Override
    protected boolean isTextValid(String s) {
        return s.matches("^[^.!?]+[.!?]$");
    }
}
