package Controllers;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.File;

/**
 * Validator for XML file.
 */
public class XMLValidator {
    /**
     * Validates XML file against a schema.
     *
     * @return true if file is valid
     */
    public static boolean validate(String xml, String xsd) {
        try {
            SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema")
                    .newSchema(new File(xsd))
                    .newValidator()
                    .validate(new StreamSource(xml));
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed validating. Error: " + e.getMessage(), e);
        }
        return true;
    }
}
