package ua.com.alus.medhosp.backend.axon.api.base;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import ua.com.alus.medhosp.backend.axon.api.patient.event.SaveAttributeValueEvent;
import ua.com.alus.medhosp.backend.axon.api.patient.event.SavePatientEvent;
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

    public static final String KEY = "key";
    public static final String ENTITY_ID = "entityId";
    public static final String ATTRIBUTE_ID = "attributeId";
    public static final String ATTRIBUTE_TYPE = "attributeType";
    public static final String ATTRIBUTE_VALUE = "attributeValue";
    public static final String ATTRIBUTE_LABEL = "attributeLabel";
    public static final String CLASS = "class";
    String SUPER_KEY_NAME = "superKeyName";

    private IJmsEventProducer iJmsEventProducer;

    @Around(value = "execution(* *EventHandler(..)) && args(event))", argNames = "pjp,event")
    public Object aroundProceedingEvent(ProceedingJoinPoint pjp, AbstractEntityEvent event) throws Throwable {
        Object result = pjp.proceed();
        Map<String, String> answer = new HashMap<String, String>();
        answer.put(MESSAGE_ID, event.getMessageId());
        fillAllParams(event, answer);
        getiJmsEventProducer().sendResult(answer);
        return result;
    }

    @AfterThrowing(value = "execution(* *EventHandler(..)) && args(event))", throwing = "e")
    public void afterThrowingException(AbstractEntityEvent event, Throwable e) {
        Map<String, String> answer = new HashMap<String, String>();
        answer.put(MESSAGE_ID, event.getMessageId());
        answer.put(ERROR, e.getMessage());
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
        answer.put(KEY, event.getEntityId());
        if (event instanceof SaveAttributeValueEvent) {
            answer.put(ATTRIBUTE_VALUE, ((SaveAttributeValueEvent) event).getAttributeValue());
            answer.put(ATTRIBUTE_ID, ((SaveAttributeValueEvent) event).getAttributeId());
            answer.put(CLASS, "PatientAttributeValue");
            answer.put(SUPER_KEY_NAME, ((SaveAttributeValueEvent) event).getAttributeId());
        } else if (event instanceof SavePatientEvent) {
            answer.put(CLASS, "PatientDTO");
        }
    }
}
