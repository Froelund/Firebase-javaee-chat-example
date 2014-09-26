package net.froelund.javaee_examples.chat.boundary;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import net.froelund.javaee_examples.chat.entity.ChatMessage;
import net.froelund.javaee_examples.chat.event.Change;
import net.froelund.javaee_examples.chat.event.Delete;
import net.froelund.javaee_examples.chat.event.New;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by froelund on 9/26/14.
 */
@Singleton
@Startup
public class ChatMessageEngine implements ChildEventListener {

    Logger logger = Logger.getLogger(ChatMessageEngine.class.getCanonicalName());

    Firebase firebase = new Firebase("https://javaee-firebase.firebaseio.com/chat");

    @Inject
    @New
    Event<ChatMessage> newChatMessageEvent;

    @Inject
    @Change
    Event<ChatMessage> changeChatMessageEvent;

    @Inject
    @Delete
    Event<ChatMessage> deleteChatMessageEvent;

    @PostConstruct
    public void init(){
        firebase.addChildEventListener(this);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        logger.log(Level.INFO, "ChatMessage added!");
        //Firebase uses Jackson for mapping https://www.firebase.com/docs/android/api/#datasnapshot_getValue
        ChatMessage chatMessage = mapChatMessage(dataSnapshot);
        newChatMessageEvent.fire(chatMessage);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        logger.log(Level.INFO, "ChatMessage changed");
        ChatMessage chatMessage = mapChatMessage(dataSnapshot);
        changeChatMessageEvent.fire(chatMessage);
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        logger.log(Level.INFO, "ChatMessage removed: {0}", dataSnapshot.getValue());
        ChatMessage chatMessage = mapChatMessage(dataSnapshot);
        deleteChatMessageEvent.fire(chatMessage);
    }

    private ChatMessage mapChatMessage(DataSnapshot dataSnapshot) {
        logger.log(Level.INFO, "Mapping {0}, name: {1}", new Object[]{dataSnapshot, dataSnapshot.getName()});
        ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
        chatMessage.setId(dataSnapshot.getName());
        return chatMessage;
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        logger.log(Level.INFO, "Child has moved..");
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {
        logger.log(Level.INFO, "Stuff went bad!");
    }
}
