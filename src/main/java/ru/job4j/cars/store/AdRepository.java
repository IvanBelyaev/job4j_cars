package ru.job4j.cars.store;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.cars.models.Advertisement;
import ru.job4j.cars.models.Brand;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class AdRepository implements Repository {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private static final class Lazy {
        private static final AdRepository INST = new AdRepository();
    }

    public static AdRepository instOf() {
        return Lazy.INST;
    }

    private AdRepository() {
    }

    @Override
    public List<Advertisement> getAdsForToday() {
        Date today = new Date();
        Date todayMorning = DateUtils.truncate(today, Calendar.DATE);
        Date todayEvening = DateUtils.addSeconds(DateUtils.addMinutes(DateUtils.addHours(todayMorning, 23), 59), 59);
        String hql = "from Advertisement a where a.publicationDate between :start and :end";
        return txFunction(
                session -> session.createQuery(hql)
                        .setParameter("start", todayMorning)
                        .setParameter("end", todayEvening)
                        .list()
        );
    }

    @Override
    public List<Advertisement> getAdsWithPhotos() {
        String hql = "from Advertisement a join fetch a.photos";
        return txFunction(
                session -> session.createQuery(hql).list()
        );
    }

    @Override
    public List<Advertisement> getAdsByBrand(Brand brand) {
        String hql = "from Advertisement a join fetch a.model m join fetch m.brand where m.brand = :brand";
        return txFunction(
                session -> session.createQuery(hql)
                        .setParameter("brand", brand)
                        .list()
        );
    }

    private <T> T txFunction(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    private void txConsumer(Consumer<Session> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            command.accept(session);
            tx.commit();
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
