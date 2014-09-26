package net.froelund.javaee_examples.chat.control;

import net.froelund.javaee_examples.chat.entity.ChatMessage;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by froelund on 9/26/14.
 */
@Stateless
public class ChatMessageService {

    @PersistenceContext
    EntityManager em;

    public void create(ChatMessage chatMessage){
        em.persist(chatMessage);
    }
    public void edit(ChatMessage chatMessage){
        em.merge(chatMessage);
    }
    public void delete(ChatMessage chatMessage){
        em.remove(chatMessage);
    }
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<ChatMessage> rt = cq.from(ChatMessage.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        javax.persistence.Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
}
