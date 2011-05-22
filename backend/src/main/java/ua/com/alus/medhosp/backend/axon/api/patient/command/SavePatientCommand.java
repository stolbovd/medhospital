package ua.com.alus.medhosp.backend.axon.api.patient.command;

import ua.com.alus.medhosp.backend.axon.api.base.AbstractEntityCommand;

/**
 * Created by Usatov Alexey
 * Date: 19.05.11
 * Time: 11:21
 */
public class SavePatientCommand extends AbstractEntityCommand {

    public SavePatientCommand() {

    }

    public SavePatientCommand(String entityId, String messageId) {
        super(entityId, messageId);
    }
}
