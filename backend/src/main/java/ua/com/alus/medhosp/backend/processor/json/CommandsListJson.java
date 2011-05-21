package ua.com.alus.medhosp.backend.processor.json;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Usatov Alexey
 * Date: 19.05.11
 * Time: 15:33
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommandsListJson {

    public CommandsListJson() {

    }

    private List<CommandJson> commands = new ArrayList<CommandJson>();

    public List<CommandJson> getCommands() {
        return commands;
    }

    @JsonProperty("commands")
    public void setCommands(List<CommandJson> commands) {
        this.commands = commands;
    }
}
