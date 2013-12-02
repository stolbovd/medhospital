package ua.com.alus.medhosp.backend.axon.api.patient.command;

import lombok.Getter;
import lombok.Setter;
import ua.com.alus.medhosp.backend.axon.api.base.AbstractEntityCommand;

/**
 * Created Usatov Alexey
 * Date: 23.05.11
 * Time: 12:13
 */
public class RemoveAttributeValueCommand extends AbstractEntityCommand {

    @Setter
    @Getter
    private String attributeId;

    public RemoveAttributeValueCommand() {

    }

    public RemoveAttributeValueCommand(String entityId, String messageId, String userId, String attributeId) {
        super(entityId, messageId, userId);
        this.attributeId = attributeId;
    }
}
