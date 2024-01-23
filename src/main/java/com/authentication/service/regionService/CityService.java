package com.authentication.service.regionService;


import com.authentication.model.region.City;

import java.util.List;

public interface CityService {


    List<City> getAllCities();

    List<City> createOrUpdateCities(List<String> citiesName,Long stateId);

    void deleteCity(Long id);
}
