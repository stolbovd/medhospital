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

    public void scheduleReSendCommand(final String messageId) {
        String jobName = "Message_" + messageId;
        try {
            getScheduler().deleteJob(jobName,"Message");
            JobDetail job = new JobDetail(jobName, "Message", BaseJob.class);
            job.setRequestsRecovery(true);
            job.setDurability(false);
            job.setVolatility(false);
            job.getJobDataMap().put(BaseJob.BEAN_ID, "reSendMessageJob");
            job.getJobDataMap().put(TaskColumns.ENTITY_ID.getColumnName(), messageId);

            Trigger trigger = new SimpleTrigger("MESS_" + System.currentTimeMillis(), "Message");
            trigger.setStartTime(new Date(System.currentTimeMillis() + getResendDelay() * 1000));
            trigger.setMisfireInstruction(CronTrigger.INSTRUCTION_RE_EXECUTE_JOB);
            getScheduler().scheduleJob(job, trigger);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
