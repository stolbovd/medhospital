package ua.com.alus.medhosp.backend.axon.api.base;

/**
 * Created by Usatov Alexey
 * Date: 19.05.11
 * Time: 13:20
 */
public abstract class AbstractEntityCommand {
    private String messageId;
    private String entityId;

    private String userId;

    /**
     * Needed for CommandProcessor
     */
    public AbstractEntityCommand() {

    }

    public AbstractEntityCommand(String entityId, String messageId, String userId) {
        this.entityId = entityId;
        this.messageId = messageId;
        this.userId = userId;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}