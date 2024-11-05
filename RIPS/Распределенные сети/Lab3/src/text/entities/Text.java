package text.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Text {
    private final static Logger logger = LogManager.getLogger();
    private final List<TextComponent> components;

    public Text(List<TextComponent> textData) throws IllegalArgumentException {
        logger.info("Create new Text object.");
        if (textData == null){
            logger.error("Text Data cannot be null");
            throw new IllegalArgumentException("Text Data cannot be null");
        }

        this.components = textData;
    }

    public List<TextComponent> getComponents() {
        return components;
    }

    @Override
    public String toString() {
        var string = new StringBuilder();
        var count = 0;
        for (var component : components) {
            string.append(component);
            if (component.getContent().length() < 20 && ++count != 5) continue;
            string.append("\n");
            count = 0;
        }

        return string.toString();
    }
}
