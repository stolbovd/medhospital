package ua.com.alus.medhosp.backend.processor;

import org.apache.log4j.Logger;
import org.axonframework.commandhandling.CommandBus;
import org.codehaus.jackson.map.ObjectMapper;
import ua.com.alus.medhosp.backend.jms.IJmsEventProducer;
import ua.com.alus.medhosp.prototype.json.CommandJson;
import ua.com.alus.medhosp.prototype.json.CommandsListJson;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Usatov Alexey
 * Date: 19.05.11
 * Time: 13:57
 */
public class CommandProcessorImpl implements ICommandProcessor {

    private Map<String, String> command2Class = new HashMap<String, String>();

    public void setCommand2Class(Map<String, String> command2class) {
        this.command2Class = command2class;
    }

    public Map<String, String> getCommand2Class() {
        return command2Class;
    }

    private Logger logger = Logger.getLogger(CommandProcessorImpl.class);

    private CommandBus commandBus;

    public void setCommandBus(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    private final ObjectMapper jsonMapper = new ObjectMapper();

    public ObjectMapper getJsonMapper() {
        return jsonMapper;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void processCommand(String json) {
        logger.trace("Processing json: " + json);
        try {
            CommandsListJson commandsData = getJsonMapper().readValue(json, CommandsListJson.class);

            for (CommandJson command : commandsData.getCommands()) {
                HashMap<String, String> properties = command.getProperties();
                Object commandObject = createObject(command.getCommand(), properties);
                if (commandObject == null) {
                    logger.trace("Cannot proceed command:" + command);
                }
                commandBus.dispatch(commandObject);
            }
        } catch (IOException e) {
            logger.trace(e);
        }

    }

    private Object createObject(String command, Map<String, String> properties) {
        try {
            if (getCommand2Class().get(command) == null) {
                logger.trace(String.format("No class is associated for command: %s", command));
                return null;
            }
            Object resultObject = Class.forName(getCommand2Class().get(command)).newInstance();
            setProperties(resultObject, resultObject.getClass(), properties);
            return resultObject;

        } catch (Exception e) {
            logger.trace(e);

        }
        return null;
    }

    private void setProperties(Object object, Class clazz, Map<String, String> properties) throws IllegalAccessException {
        if (clazz.getSuperclass() != null) {
            setProperties(object, clazz.getSuperclass(), properties);
        }
        for (Field field : clazz.getDeclaredFields()) {
            if (properties.get(field.getName()) != null) {
                int mod = field.getModifiers();
                if (Modifier.isFinal(mod) ||
                        Modifier.isStatic(mod) ||
                        field.isEnumConstant() ||
                        Modifier.isTransient(mod)) {
                    continue;
                }
                field.setAccessible(true);
                field.set(object, properties.get(field.getName()));
                field.setAccessible(false);
            }
        }
    }
}
