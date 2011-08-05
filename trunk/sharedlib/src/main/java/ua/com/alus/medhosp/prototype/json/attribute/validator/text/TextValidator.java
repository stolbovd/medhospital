package ua.com.alus.medhosp.prototype.json.attribute.validator.text;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import ua.com.alus.medhosp.prototype.json.attribute.validator.IValidator;
import ua.com.alus.medhosp.prototype.json.attribute.validator.ValidatorErrorCodes;

/**
 * Text Validator
 * <p/>
 * Created by Usatov Alexey
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class TextValidator implements IValidator {
    private int min = -1;
    private int max = -1;
    private String restrictSymbols;

    @SuppressWarnings("unchecked")
    public String validate(Object value) {
        if (value == null) {
            return null;
        }
        String row = (String) value;
        if (min != -1) {
            if (row.length() < min) {
                return ValidatorErrorCodes.TEXT_001.getCode();
            }
        }
        if (max != -1) {
            if (row.length() > max) {
                return ValidatorErrorCodes.TEXT_002.getCode();
            }
        }
        return null;
    }

    public int getMin() {
        return min;
    }

    @JsonProperty("min")
    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    @JsonProperty("max")
    public void setMax(int max) {
        this.max = max;
    }

    public String getRestrictSymbols() {
        return restrictSymbols;
    }

    @JsonProperty("restrictSymbols")
    public void setRestrictSymbols(String restrictSymbols) {
        this.restrictSymbols = restrictSymbols;
    }
}
