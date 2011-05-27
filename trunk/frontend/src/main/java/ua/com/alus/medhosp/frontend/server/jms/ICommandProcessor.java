package ua.com.alus.medhosp.frontend.server.jms;

/**
 * Created Usatov Alexey
 * Date: 27.05.11
 * Time: 11:11
 */
public interface ICommandProcessor {
    void processCommand(String json);
}
