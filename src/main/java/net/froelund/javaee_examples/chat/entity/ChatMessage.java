package net.froelund.javaee_examples.chat.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by froelund on 9/26/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class ChatMessage implements Serializable{
    @Id
    String id;
    String message;
    String sender;

    public ChatMessage() {
    }

    public ChatMessage(String id, String message, String sender) {
        this.id = id;
        this.message = message;
        this.sender = sender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "ChatEvent{" +
                "sender='" + sender + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
