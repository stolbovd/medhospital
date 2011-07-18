package ua.com.alus.medhosp.backend.axon.api.base;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import ua.com.alus.medhosp.backend.axon.api.patient.event.SaveAttributeValueEvent;
import ua.com.alus.medhosp.backend.axon.api.patient.event.SavePatientEvent;
import ua.com.alus.medhosp.backend.jms.IJmsEventProducer;
import ua.com.alus.medhosp.prototype.cassandra.dto.AttributeValueColumns;
import ua.com.alus.medhosp.prototype.cassandra.dto.BaseColumns;
import ua.com.alus.medhosp.prototype.cassandra.dto.Dto;
import ua.com.alus.medhosp.prototype.data.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created Usatov Alexey
 * Date: 27.05.11
 * Time: 12:29
 */
@Aspect
public class EventHandlerAspect {


    private IJmsEventProducer iJmsEventProducer;

    @Around(value = "execution(* *EventHandler(..)) && args(event))", argNames = "pjp,event")
    public Object aroundProceedingEvent(ProceedingJoinPoint pjp, AbstractEntityEvent event) throws Throwable {
        Object result = pjp.proceed();
        Map<String, String> answer = new HashMap<String, String>();
        answer.put(Constants.MESSAGE_ID, event.getMessageId());
        fillAllParams(event, answer);
        getiJmsEventProducer().sendResult(answer);
        return result;
    }

    @AfterThrowing(value = "execution(* *EventHandler(..)) && args(event))", throwing = "e")
    public void afterThrowingException(AbstractEntityEvent event, Throwable e) {
        Map<String, String> answer = new HashMap<String, String>();
        answer.put(Constants.MESSAGE_ID, event.getMessageId());
        answer.put(Constants.ERROR, e.getMessage());
        fillAllParams(event, answer);
        getiJmsEventProducer().sendResult(answer);
    }

    public IJmsEventProducer getiJmsEventProducer() {
        return iJmsEventProducer;
    }

    public void setiJmsEventProducer(IJmsEventProducer iJmsEventProducer) {
        this.iJmsEventProducer = iJmsEventProducer;
    }

    private void fillAllParams(AbstractEntityEvent event, Map<String, String> answer) {
        answer.put(BaseColumns.ENTITY_ID.getColumnName(), event.getEntityId());
        answer.put(Constants.GOAL, event.getGoal().name());
        answer.put(Constants.USER_ID, event.getUserId());
        if (event instanceof SaveAttributeValueEvent) {
            answer.put(AttributeValueColumns.ATTRIBUTE_VALUE.getColumnName(), ((SaveAttributeValueEvent) event).getAttributeValue());
            answer.put(AttributeValueColumns.ATTRIBUTE_ID.getColumnName(), ((SaveAttributeValueEvent) event).getAttributeId());
            answer.put(Constants.CLASS, Dto.PATIENT_ATTRIBUTE_VALUE.getDtoName());
            answer.put(AttributeValueColumns.SUPER_KEY_NAME.getColumnName(), ((SaveAttributeValueEvent) event).getAttributeId());
        } else if (event instanceof SavePatientEvent) {
            answer.put(Constants.CLASS, Dto.PATIENT.getDtoName());
        }
    }
}
