package ua.com.alus.medhosp.backend.processor.json;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.HashMap;

/**
 * Created by Usatov Alexey
 * Date: 19.05.11
 * Time: 15:27
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommandJson {

    public CommandJson() {

    }

    private String command;


    private HashMap<String, String> properties;

    public String getCommand() {
        return command;
    }

    @JsonProperty("command")
    public void setCommand(String command) {
        this.command = command;
    }

    public HashMap<String, String> getProperties() {
        return properties;
    }

    @JsonProperty("properties")
    public void setProperties(HashMap<String, String> properties) {
        this.properties = properties;
    }
}
