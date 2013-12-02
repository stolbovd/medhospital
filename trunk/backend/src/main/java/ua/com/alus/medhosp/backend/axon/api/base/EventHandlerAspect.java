package ua.com.alus.medhosp.backend.axon.api.base;

import lombok.Getter;
import lombok.Setter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import ua.com.alus.medhosp.backend.axon.api.patient.command.RemoveAttributeValueCommand;
import ua.com.alus.medhosp.backend.axon.api.patient.event.*;
import ua.com.alus.medhosp.backend.jms.IJmsEventProducer;
import ua.com.alus.medhosp.prototype.cassandra.dto.AttributeColumns;
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
    @Setter
    @Getter
    private IJmsEventProducer iJmsEventProducer;

    @Around(value = "execution(* *EventHandler(..)) && args(event))", argNames = "pjp,event")
    public Object aroundProceedingEvent(ProceedingJoinPoint pjp, AbstractEntityEvent event) throws Throwable {
        Object result = pjp.proceed();
        Map<String, String> answer = new HashMap<String, String>();
        answer.put(Constants.MESSAGE_ID, event.getMessageId());
        fillAllParams(event, answer);
        getIJmsEventProducer().sendResult(answer);
        return result;
    }

    @AfterThrowing(value = "execution(* *CommandHandler(..)) && args(command))", throwing = "e")
    public void afterThrowingException(AbstractEntityCommand command, Throwable e) {
        Map<String, String> answer = new HashMap<String, String>();
        answer.put(Constants.MESSAGE_ID, command.getMessageId());
        answer.put(Constants.ERROR, e.getMessage());
        getIJmsEventProducer().sendResult(answer);
    }

    //TODO looks a little ugly :)
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
        } else if(event instanceof SaveAttributeEvent){
            answer.put(AttributeColumns.NAME.getColumnName(),  ((SaveAttributeEvent) event).getName());
            answer.put(AttributeColumns.DEFINTITION.getColumnName(), ((SaveAttributeEvent) event).getDefinition());
            answer.put(Constants.CLASS, Dto.ATTRIBUTE.getDtoName());
        }else if(event instanceof RemovePatientEvent){
            answer.put(Constants.CLASS, Dto.PATIENT.getDtoName());
        } else if(event instanceof RemoveAttributeValueEvent){
            answer.put(AttributeValueColumns.ATTRIBUTE_ID.getColumnName(), ((RemoveAttributeValueEvent) event).getAttributeId());
            answer.put(AttributeValueColumns.SUPER_KEY_NAME.getColumnName(), ((RemoveAttributeValueEvent) event).getAttributeId());
            answer.put(Constants.CLASS, Dto.PATIENT_ATTRIBUTE_VALUE.getDtoName());
        }
    }
}
