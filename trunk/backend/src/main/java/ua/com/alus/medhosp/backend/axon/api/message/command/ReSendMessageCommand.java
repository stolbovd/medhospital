package ua.com.alus.medhosp.backend.axon.api.message.command;


import ua.com.alus.medhosp.backend.axon.api.base.AbstractEntityCommand;

/**
 * ReSend message Command
 * <p/>
 * Create by Usatov Alexey
 */
public class ReSendMessageCommand extends AbstractEntityCommand {
    public ReSendMessageCommand() {

    }
    public ReSendMessageCommand(String entityId, String messageId, String userId) {
        super(entityId, messageId, userId);
    }

}
