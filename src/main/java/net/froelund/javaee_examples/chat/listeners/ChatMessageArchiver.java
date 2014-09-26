package net.froelund.javaee_examples.chat.listeners;

import net.froelund.javaee_examples.chat.control.ChatMessageService;
import net.froelund.javaee_examples.chat.entity.ChatMessage;
import net.froelund.javaee_examples.chat.event.Change;
import net.froelund.javaee_examples.chat.event.Delete;
import net.froelund.javaee_examples.chat.event.New;

import javax.ejb.Singleton;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.logging.Logger;

/**
 * Created by froelund on 9/26/14.
 */
@Singleton
public class ChatMessageArchiver {

    Logger logger = Logger.getLogger(ChatMessageArchiver.class.getCanonicalName());

    @Inject
    ChatMessageService chatMessageService;

    public void newChatMessage(@Observes @New ChatMessage chatMessage){
        chatMessageService.create(chatMessage);
    }

    public void changeChatMessage(@Observes @Change ChatMessage chatMessage){
        chatMessageService.edit(chatMessage);
    }

    public void deleteChatMessage(@Observes @Delete ChatMessage chatMessage){
        chatMessageService.delete(chatMessage);
    }
}
