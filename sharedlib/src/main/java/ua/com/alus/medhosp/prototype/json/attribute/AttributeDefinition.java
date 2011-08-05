package ua.com.alus.medhosp.prototype.json.attribute;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import ua.com.alus.medhosp.prototype.json.attribute.validator.IValidator;

import java.util.ArrayList;

/**
 * Class that represent json in column 'DEFINITION' of Attribute table
 *
 * Created by Usatov Alexey
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AttributeDefinition {
    @JsonProperty("validators")
    private ArrayList<IValidator> validators = new ArrayList<IValidator>();

    public ArrayList<IValidator> getValidators() {
        return validators;
    }

    public void setValidators(ArrayList<IValidator> validators) {
        this.validators = validators;
    }
}
