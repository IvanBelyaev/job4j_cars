package ru.job4j.cars.store;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.cars.models.*;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @Override
    public List<Brand> getAllBrands() {
        return txFunction(
                session -> session.createQuery("from Brand").list()
        );
    }

    @Override
    public List<Model> getModelsByBrand(Brand brand) {
        return txFunction(
                session -> session.createQuery("from Model m where m.brand.id = :brandId")
                        .setParameter("brandId", brand.getId())
                        .list()
        );
    }

    @Override
    public void addUser(User user) {
        txConsumer(
                session -> session.save(user)
        );
    }

    @Override
    public User getUserByPhone(String phone) {
        return txFunction(
                session -> session.createQuery("from User u where u.phone = :searchPhone", User.class)
                        .setParameter("searchPhone", phone)
                        .uniqueResult()
        );
    }

    @Override
    public List<Advertisement> getAdsByUser(User user) {
        String hql = "select distinct a " +
                "from Advertisement a " +
                "join fetch a.bodyType " +
                "join fetch a.model m " +
                "join fetch m.brand " +
                "left outer join fetch a.photos " +
                "where a.author.id = :searchUserId";
        return txFunction(
                session -> session.createQuery(hql)
                        .setParameter("searchUserId", user.getId())
                        .list()
        );
    }

    @Override
    public Model getModelById(int id) {
        return txFunction(
                session -> session.get(Model.class, id)
        );
    }

    @Override
    public BodyType getBodyTypeById(int id) {
        return txFunction(
                session -> session.get(BodyType.class, id)
        );
    }

    @Override
    public void saveAd(Advertisement ad) {
        txConsumer(
                session -> session.save(ad)
        );
    }

    @Override
    public Advertisement getAdById(long id) {
        String hql = "from Advertisement a " +
                "join fetch a.bodyType " +
                "join fetch a.model " +
                "join fetch a.model.brand " +
                "where a.id = :advertId";
        return txFunction(
                session -> {
                    Advertisement ad = session.createQuery(hql, Advertisement.class)
                            .setParameter("advertId", id)
                            .uniqueResult();
                    List<Photo> photos = session.createQuery("from Photo p where p.advertisement.id = :advertId", Photo.class)
                            .setParameter("advertId", id).list();
                    ad.setPhotos(new HashSet<>(photos));
                    return ad;
                }
        );
    }

    @Override
    public void savePhoto(Photo photo) {
        txConsumer(
                session -> session.save(photo)
        );
    }

    @Override
    public void deletePhoto(Photo photo) {
        txConsumer(
                session -> session.delete(photo)
        );
    }

    @Override
    public void changeAdStatus(long adId, boolean status) {
        txConsumer(
                session ->
                        session.createQuery("update Advertisement set status = :newStatus where id = :adId")
                                .setParameter("newStatus", status)
                                .setParameter("adId", adId)
                                .executeUpdate()
        );
    }

    @Override
    public void deleteAd(long adId) {
        txConsumer(
                session -> {
                    Advertisement advertisement = session.get(Advertisement.class, adId);
                    session.delete(advertisement);
                }
        );
    }

    @Override
    public List<Photo> getPhotosOfAd(long advertId) {
        return txFunction(
                session -> session.createQuery("from Photo where advertisement.id = :adId")
                        .setParameter("adId", advertId)
                        .list()
        );
    }

    @Override
    public List<Advertisement> getFilteredAds(
            String brand, String model,
            String priceFrom, String priceTo,
            String yearFrom, String yearTo,
            String bodyType, String withPhoto
    ) {
        String select = "select distinct a from Advertisement a " +
                "join fetch a.model m " +
                "join fetch m.brand " +
                "left outer join fetch a.photos " +
                "where a.status is false";
        List<String> whereClauses = new ArrayList<>();
        if (brand != null && !brand.equals("-1")) {
            whereClauses.add(" and a.model.brand.id = " + brand);
        }
        if (model != null && !model.equals("-1")) {
            whereClauses.add(" and a.model.id = " + model);
        }
        if (!priceFrom.isEmpty()) {
            whereClauses.add(" and a.price > " + priceFrom);
        }
        if (!priceTo.isEmpty()) {
            whereClauses.add(" and a.price < " + priceTo);
        }
        if (!yearFrom.isEmpty()) {
            whereClauses.add(" and a.year > " + yearFrom);
        }
        if (!yearTo.isEmpty()) {
            whereClauses.add(" and a.year < " + yearTo);
        }
        if (bodyType != null && !bodyType.equals("-1")) {
            whereClauses.add(" and a.bodyType.id = " + bodyType);
        }
        if (withPhoto != null) {
            whereClauses.add(" and a.photos.size > 0");
        }
        String whereClause = whereClauses.stream().collect(Collectors.joining(" "));
        String hql = select + whereClause;
        return txFunction(
                session -> session.createQuery(hql).list()
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
