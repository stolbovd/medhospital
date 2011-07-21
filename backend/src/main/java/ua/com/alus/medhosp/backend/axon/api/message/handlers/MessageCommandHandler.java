package ua.com.alus.medhosp.backend.axon.api.message.handlers;

import org.apache.log4j.Logger;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.domain.StringAggregateIdentifier;
import org.axonframework.repository.AggregateNotFoundException;
import org.axonframework.repository.Repository;
import ua.com.alus.medhosp.backend.axon.api.message.MessageAggregate;
import ua.com.alus.medhosp.backend.axon.api.message.command.ReSendMessageCommand;

/**
 * MessageCommandHandler
 *
 * Created by Usatov Alexey
 */
public class MessageCommandHandler {

    private Logger logger = Logger.getLogger(this.getClass());

    private Repository<MessageAggregate> repository;

    public void setRepository(Repository<MessageAggregate> repository) {
        this.repository = repository;
    }

    @CommandHandler
    public void resendMessage(ReSendMessageCommand command){
        MessageAggregate messageAggregate;
        try {
            messageAggregate = repository.load(new StringAggregateIdentifier(command.getEntityId()));
        } catch (AggregateNotFoundException agnf) {
            logger.info("Aggregate MessageAggregate not found, adding to repository");
            messageAggregate = new MessageAggregate(command.getEntityId());
            repository.add(messageAggregate);
        }
        messageAggregate.resendMessage(command.getMessageId(), command.getUserId());
        logger.debug("Handling re-send message command");
    }
}
