package sbal.stels;

import javax.persistence.*;
import java.util.List;
import java.util.concurrent.Callable;

public class DbManager {
    private EntityManager em;

    public DbManager(EntityManager em) {
        this.em = em;
    }

    private <T> T performTransaction(Callable<T> action) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            T result = action.call();
            transaction.commit();

            return result;
        } catch (Exception ex) {
            if (transaction.isActive())
                transaction.rollback();

            throw new RuntimeException(ex);
        }
    }

    public List<Menu> stringQuery(final Query query) {
        return performTransaction(new Callable<List<Menu> >() {
            public List<Menu> call() throws RuntimeException {
                List<Menu> list = null;
                List lst = query.getResultList();
                if (lst != null) {
                    list = (List<Menu>) query.getResultList();
                }
                return list;
            }
        });
    }

    public List<Menu> viewMenu(){
        return stringQuery(em.createQuery("SELECT m FROM Menu m"));
    }

    public List<Menu> viewMenu(Integer fromCost, Integer toCost){
        return stringQuery(em.createQuery(
                "SELECT m FROM Menu m WHERE m.cost > :frm AND m.cost < :to ORDER BY m.cost").setParameter(
                        "frm", fromCost).setParameter("to", toCost));
    }

    public List<Menu> viewDiscounts(){
        return stringQuery(em.createQuery("SELECT m FROM Menu m WHERE m.isDiscount = TRUE"));
    }

    public List<Menu> viewLightDishes(){
        return stringQuery(em.createQuery("SELECT m FROM Menu m WHERE m.weight < 1"));
    }

    public void deleteClient(final long id) {
        performTransaction(new Callable<Void>() {
            @Override
            public Void call() {
                em.remove(em.getReference(Menu.class, id));
                return null;
            }
        });
    }

    public void addClient(final Menu menu) {
        performTransaction(new Callable<Void>() {
            @Override
            public Void call() {
                em.persist(menu);
                return null;
            }
        });
    }
}
