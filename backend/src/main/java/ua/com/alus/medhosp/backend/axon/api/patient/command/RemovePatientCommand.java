package ua.com.alus.medhosp.backend.axon.api.patient.command;

import ua.com.alus.medhosp.backend.axon.api.base.AbstractEntityCommand;

/**
 * Created by IntelliJ IDEA.
 * User: Usatov Alexey
 * Date: 19.05.11
 * Time: 12:43
 */
public class RemovePatientCommand extends AbstractEntityCommand {
    public RemovePatientCommand() {

    }

    public RemovePatientCommand(String entityId, String messageId) {
        super(entityId, messageId);
    }
}
