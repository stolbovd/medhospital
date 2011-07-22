package ua.com.alus.medhosp.frontend.server.quartz;

import org.quartz.*;
import ua.com.alus.medhosp.prototype.cassandra.dto.TaskColumns;

import java.util.Date;

/**
 * Message utils
 * <p/>
 * Created by Usatov Alexey
 */
public class MessageUtilsBean {

    private Scheduler scheduler;

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    private int resendDelay = 10;

    public void setResendDelay(int resendDelay) {
        this.resendDelay = resendDelay;
    }

    public int getResendDelay() {
        return resendDelay;
    }

    private static final String MESSAGE = "Message";
    private static final String RESEND_MESS_BEAN_ID = "reSendMessageJob";
    private static final String TRIGGER_PREFIX = "MESS_";
    private static final String NAME_PREFIX = "Message_";


    public void scheduleReSendCommand(final String messageId) {
        String jobName = NAME_PREFIX + messageId;
        try {
            getScheduler().deleteJob(jobName, MESSAGE);
            JobDetail job = new JobDetail(jobName, MESSAGE, BaseJob.class);
            job.setRequestsRecovery(true);
            job.setDurability(false);
            job.setVolatility(false);
            job.getJobDataMap().put(BaseJob.BEAN_ID, RESEND_MESS_BEAN_ID);
            job.getJobDataMap().put(TaskColumns.ENTITY_ID.getColumnName(), messageId);

            Trigger trigger = new SimpleTrigger(TRIGGER_PREFIX + System.currentTimeMillis(), MESSAGE);
            trigger.setStartTime(new Date(System.currentTimeMillis() + getResendDelay() * 1000));
            trigger.setMisfireInstruction(CronTrigger.INSTRUCTION_RE_EXECUTE_JOB);
            getScheduler().scheduleJob(job, trigger);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
