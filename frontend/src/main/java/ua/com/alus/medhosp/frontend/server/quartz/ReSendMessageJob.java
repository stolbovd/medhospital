package ua.com.alus.medhosp.frontend.server.quartz;

import org.apache.log4j.Logger;
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
    private Logger logger = Logger.getLogger(ReSendMessageJob.class);

    private ICommandProducer commandProducer;

    public ICommandProducer getCommandProducer() {
        return commandProducer;
    }

    public void setCommandProducer(ICommandProducer commandProducer) {
        this.commandProducer = commandProducer;
    }

    private MessageUtilsBean messageUtilsBean;

    public MessageUtilsBean getMessageUtilsBean() {
        return messageUtilsBean;
    }

    public void setMessageUtilsBean(MessageUtilsBean messageUtilsBean) {
        this.messageUtilsBean = messageUtilsBean;
    }

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String messageId = null;
        try {
            messageId = (String) jobExecutionContext.getJobDetail().getJobDataMap().get(TaskColumns.ENTITY_ID.getColumnName());
            getCommandProducer().generateCommands(getResendCommandList(messageId));
        } catch (Throwable e) {
            logger.error(e);
            if (messageId != null) {
                logger.info("Re-scheduling requesting for re-send unproceceeded message...");
                getMessageUtilsBean().scheduleReSendCommand(messageId);
            }
        }
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
