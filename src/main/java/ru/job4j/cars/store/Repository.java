package ru.job4j.cars.store;

import ru.job4j.cars.models.*;

import java.util.List;

public interface Repository {
    List<Advertisement> getAdsForToday();
    List<Advertisement> getAdsWithPhotos();
    List<Advertisement> getAdsByBrand(Brand brand);
    List<Brand> getAllBrands();
    List<Model> getModelsByBrand(Brand brand);
    void addUser(User user);
    User getUserByPhone(String phone);
    List<Advertisement> getAdsByUser(User user);
    Model getModelById(int id);
    BodyType getBodyTypeById(int id);
    void saveAd(Advertisement ad);
    Advertisement getAdById(long id);
    void savePhoto(Photo photo);
    void deletePhoto(Photo photo);
    void changeAdStatus(long adId, boolean status);
    void deleteAd(long adId);
    List<Photo> getPhotosOfAd(long advertId);

    List<Advertisement> getFilteredAds(
            String brand, String model,
            String priceFrom, String priceTo,
            String yearFrom, String yearTo,
            String bodyType, String withPhoto
    );
}
