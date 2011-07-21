package ua.com.alus.medhosp.frontend.server.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import ua.com.alus.medhosp.frontend.server.jms.ICommandProducer;
import ua.com.alus.medhosp.prototype.cassandra.dto.TaskColumns;
import ua.com.alus.medhosp.prototype.commands.Command;
import ua.com.alus.medhosp.prototype.json.CommandJson;
import ua.com.alus.medhosp.prototype.json.CommandsListJson;

/**
 * Job for re-send Message
 * <p/>
 * Created by Usatov Alexey
 */
public class ReSendMessageJob implements Job {
    private ICommandProducer commandProducer;

    public ICommandProducer getCommandProducer() {
        return commandProducer;
    }

    public void setCommandProducer(ICommandProducer commandProducer) {
        this.commandProducer = commandProducer;
    }

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String messageId = (String) jobExecutionContext.getJobDetail().getJobDataMap().get(TaskColumns.ENTITY_ID.getColumnName());
        getCommandProducer().generateCommands(getResendCommandList(messageId));
    }

    private CommandsListJson getResendCommandList(String messageId) {
        CommandsListJson commandsListJson = new CommandsListJson();
        CommandJson reSendMessCommand = new CommandJson();
        reSendMessCommand.setCommand(Command.RESEND_MESSAGE.getCommandName());
        reSendMessCommand.getProperties().put(TaskColumns.ENTITY_ID.getColumnName(), messageId);
        commandsListJson.getCommands().add(reSendMessCommand);
        return commandsListJson;
    }
}
