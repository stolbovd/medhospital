package ua.com.alus.medhosp.frontend.server.jms;

import ua.com.alus.medhosp.prototype.json.CommandsListJson;

import javax.jms.JMSException;


/**
 * Interface for commandProducers
 * <p/>
 * Created by Usatov Alexey
 */
public interface ICommandProducer {
    void generateCommands(final CommandsListJson commandsListJson) throws RuntimeException;
}
