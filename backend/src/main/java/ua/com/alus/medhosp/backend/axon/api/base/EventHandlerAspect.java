package ua.com.alus.medhosp.backend.axon.api.base;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import ua.com.alus.medhosp.backend.jms.IJmsEventProducer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created Usatov Alexey
 * Date: 27.05.11
 * Time: 12:29
 */
@Aspect
public class EventHandlerAspect {
    public static final String ERROR = "error";
    public static final String MESSAGE_ID = "messageId";
    public static final String RESULT = "result";

    private IJmsEventProducer iJmsEventProducer;

    @Around(value = "execution(* *EventHandler(..)) && args(event))", argNames = "pjp,event")
    public Object aroundProceedingEvent(ProceedingJoinPoint pjp, AbstractEntityEvent event) throws Throwable {
        Object result = pjp.proceed();
        Map<String, String> answer = new HashMap<String, String>();
        answer.put(MESSAGE_ID, event.getMessageId());
        getiJmsEventProducer().sendResult(answer);
        return result;
    }

    @AfterThrowing(value = "execution(* *EventHandler(..)) && args(event))", throwing = "e")
    public void afterThrowingException(AbstractEntityEvent event, Throwable e) {
        Map<String, String> answer = new HashMap<String, String>();
        answer.put(MESSAGE_ID, event.getMessageId());
        answer.put(ERROR, e.getMessage());
        getiJmsEventProducer().sendResult(answer);
    }

    public IJmsEventProducer getiJmsEventProducer() {
        return iJmsEventProducer;
    }

    public void setiJmsEventProducer(IJmsEventProducer iJmsEventProducer) {
        this.iJmsEventProducer = iJmsEventProducer;
    }
}
