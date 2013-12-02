package ua.com.alus.medhosp.backend.axon.api.base;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Usatov Alexey
 * Date: 19.05.11
 * Time: 13:20
 */

@AllArgsConstructor(access = AccessLevel.PUBLIC)
public abstract class AbstractEntityCommand {
    @Setter
    @Getter
    private String messageId;
    @Setter
    @Getter
    private String entityId;
    @Setter
    @Getter
    private String userId;

    /**
     * Needed for CommandProcessor
     */
    public AbstractEntityCommand() {

    }
}
