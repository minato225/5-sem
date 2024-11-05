package text.entities;

public abstract class TextComponent {
    protected String content;

    public TextComponent(String content) throws IllegalArgumentException{
        if (content == null || content.isEmpty())
            throw new IllegalArgumentException("The content cannot be null or empty.");
        if(!isTextValid(content))
            throw new IllegalArgumentException("The content isn't match required form.");

        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    protected abstract boolean isTextValid(String s);
    protected abstract String name();

    @Override
    public String toString() {
        return "%s={ %s } ".formatted(name(), content);
    }
}
