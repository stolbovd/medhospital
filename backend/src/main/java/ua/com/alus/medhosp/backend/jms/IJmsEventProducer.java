package ua.com.alus.medhosp.backend.jms;

import java.util.Map;

/**
 * Created Usatov Alexey
 * Date: 27.05.11
 * Time: 11:26
 */
public interface IJmsEventProducer {
    void sendResult(final Map<String,String> map);
}
