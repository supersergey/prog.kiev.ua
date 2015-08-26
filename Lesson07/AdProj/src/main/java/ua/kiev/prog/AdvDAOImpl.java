package ua.kiev.prog;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.transaction.Transaction;
import javax.transaction.TransactionRequiredException;
import java.util.List;

public class AdvDAOImpl implements AdvDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Advertisement> list() {
        final Query query = entityManager.createQuery("SELECT a FROM Advertisement a WHERE a.inRecycled=false", Advertisement.class);
        return (List<Advertisement>) query.getResultList();
    }

    @Override
    public List<Advertisement> list(String pattern) {
        final Query query = entityManager.createQuery("SELECT a FROM Advertisement a WHERE a.inRecycled=false AND a.shortDesc LIKE :pattern", Advertisement.class);
        query.setParameter("pattern", "%" + pattern + "%");
        return (List<Advertisement>) query.getResultList();
    }

    @Override
    public List<Advertisement> listRecycled() {
        final Query query = entityManager.createQuery("SELECT a FROM Advertisement a WHERE a.inRecycled=true", Advertisement.class);
        return (List<Advertisement>) query.getResultList();
    }

    @Override
    public void moveToRecycled(long id) {
        final Query query = entityManager.createQuery("UPDATE Advertisement a SET a.inRecycled = true WHERE a.id=:id");
        query.setParameter("id", id);
        executeUpdateTransaction(query);
    }

    @Override
    public void moveToRecycled(List<Long> ids) {
        final Query query = entityManager.createQuery("UPDATE Advertisement a SET a.inRecycled = true WHERE a.id IN :ids");
        query.setParameter("ids", ids);
        executeUpdateTransaction(query);
    }

    @Override
    public void restoreFromRecycled(List<Long> ids) {
        final Query query = entityManager.createQuery("UPDATE Advertisement a SET a.inRecycled = false WHERE a.id IN :ids");
        query.setParameter("ids", ids);
        executeUpdateTransaction(query);
    }

    @Override
    public void restoreFromRecycled(long id) {
        final Query query = entityManager.createQuery("UPDATE Advertisement a SET a.inRecycled = false WHERE a.id=:id");
        query.setParameter("id", id);
        executeUpdateTransaction(query);
    }

    @Override
    public void emptyRecycled() {
        final Query query = entityManager.createQuery("DELETE Advertisement a where a.inRecycled = true");
        executeUpdateTransaction(query);
    }

    @Override
    public void add(Advertisement adv) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(adv);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            ex.printStackTrace();
        }
    }

    /*@Override
    public void delete(long id) {
        try {
            entityManager.getTransaction().begin();
            Advertisement adv = entityManager.find(Advertisement.class, id);
            entityManager.remove(adv);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            ex.printStackTrace();
        }
    }*/

    @Override
    public byte[] getPhoto(long id) {
        try {
            Photo photo = entityManager.find(Photo.class, id);
            return photo.getBody();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private void executeUpdateTransaction(Query query)
    {
        final EntityTransaction tr = entityManager.getTransaction();
        tr.begin();
        try
        {
            query.executeUpdate();
            tr.commit();
        }
        catch (Exception ex)
        {
            tr.rollback();
            ex.printStackTrace();
        }
    }
}
