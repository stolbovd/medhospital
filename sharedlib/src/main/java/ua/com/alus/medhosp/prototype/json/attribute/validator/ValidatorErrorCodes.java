package ua.com.alus.medhosp.prototype.json.attribute.validator;


/**
 * Enumeration of error codes of
 * <p/>
 * Created by Usatov Alexey
 */
public enum ValidatorErrorCodes {

    TEXT_001("text.001", "Value less then min"),
    TEXT_002("text.002", "Value more then max");

    ValidatorErrorCodes(String code, String description) {
        this.code = code;
    }

    private String code;

    public String getCode() {
        return code;
    }

    private String description;

    public String getDescription() {
        return description;
    }
}
