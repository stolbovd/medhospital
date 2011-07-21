package ua.com.alus.medhosp.frontend.server.quartz;

import org.apache.log4j.Logger;
import org.quartz.*;
import org.springframework.context.ApplicationContext;

public class BaseJob implements Job {

    private static final Logger logger = Logger.getLogger(BaseJob.class);

    public static final String BEAN_ID = "beanId";

    public static final String APPLICATION_CONTEXT_KEY = "applicationContext";

    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String beanId = jobDataMap.getString(BEAN_ID);
        try {
            getJobBean(context, beanId).execute(context);
        } catch (Exception ignored) {
            logger.error(ignored.getMessage(), ignored);
        }
    }

    protected Object getBean(JobExecutionContext context, String beanId) throws SchedulerException {
        ApplicationContext appCtx = (ApplicationContext) context.getScheduler().getContext().get(APPLICATION_CONTEXT_KEY);
        if (appCtx == null) {
            throw new JobExecutionException("No application context available in scheduler context for key \"" + APPLICATION_CONTEXT_KEY + "\"");
        }
        return appCtx.getBean(beanId);
    }

    private org.quartz.Job getJobBean(JobExecutionContext context, String beanId) throws SchedulerException {
        return (Job) getBean(context, beanId);
    }

}