package ru.job4j.cars.store;

import ru.job4j.cars.models.Advertisement;
import ru.job4j.cars.models.Brand;

import java.util.List;

public interface Repository {
    List<Advertisement> getAdsForToday();
    List<Advertisement> getAdsWithPhotos();
    List<Advertisement> getAdsByBrand(Brand brand);
}
