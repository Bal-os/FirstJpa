package sbal.stels.test;

import org.junit.After;
import org.junit.Before;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import sbal.stels.*;
import java.util.concurrent.Callable;

import static org.junit.Assert.assertEquals;

public class BaseTest {
    private static final String NAME = "JPATest";

    private EntityManagerFactory emFactory;
    private EntityManager em;
    private DbManager manager;

    @Before
    public void init() {
        emFactory = Persistence.createEntityManagerFactory(NAME);
        em = emFactory.createEntityManager();
        manager = new DbManager(em);
    }

    @After
    public void finish() {
        if (em != null) em.close();
        if (emFactory != null) emFactory.close();
    }

    private void saveFewDishes() {
        List<Menu> dishes = Arrays.asList(
                new Menu("Borshch", 123, 1.000001),
                new Menu("Borshch", 123, 0.999999, true),
                new Menu("Kulish", 100, 0.3),
                new Menu("Big Mac", 50, 0.2, false),
                new Menu("Okroshka", 80, 0.4));

        for(Menu d: dishes){
            manager.addClient(d);
        }
    }

    @Test
    public void testPersist() {
        saveFewDishes();

        List<Menu> res1 = manager.viewMenu();
        assertEquals(5, res1.size());

        List<Menu> res2 = manager.viewDiscounts();
        assertEquals(1, res2.size());

        List<Menu> res3 = manager.viewMenu(80, 100);
        assertEquals(0, res3.size());

        List<Menu> res4 = manager.viewMenu(79, 101);
        assertEquals(2, res4.size());

        List<Menu> res5 = manager.viewLightDishes();
        assertEquals(4, res5.size());


    }

}
