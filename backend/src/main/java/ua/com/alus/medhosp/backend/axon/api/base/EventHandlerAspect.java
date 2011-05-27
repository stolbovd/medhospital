package ua.com.alus.medhosp.backend.axon.api.base;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import ua.com.alus.medhosp.backend.jms.IJmsEventProducer;

/**
 * Created Usatov Alexey
 * Date: 27.05.11
 * Time: 12:29
 */
@Aspect
public class EventHandlerAspect {
    public static final String OK = "ok";
    public static final String ERROR = "error";
    private IJmsEventProducer iJmsEventProducer;

    @Around(value = "execution(* *EventHandler(..)) && args(event))", argNames = "pjp,event")
    public Object aroundProceedingEvent(ProceedingJoinPoint pjp, AbstractEntityEvent event) throws Throwable{
        Object result = pjp.proceed();
        getiJmsEventProducer().sendResult(event.getMessageId(), OK);
        return result;
    }

    @AfterThrowing(value = "execution(* *EventHandler(..)) && args(event))", throwing = "e")
    public void afterThrowingException(AbstractEntityEvent event, Throwable e){
        getiJmsEventProducer().sendResult(event.getMessageId(), ERROR);
    }

    public IJmsEventProducer getiJmsEventProducer() {
        return iJmsEventProducer;
    }

    public void setiJmsEventProducer(IJmsEventProducer iJmsEventProducer) {
        this.iJmsEventProducer = iJmsEventProducer;
    }
}
