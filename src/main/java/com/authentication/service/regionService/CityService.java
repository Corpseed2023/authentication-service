package com.authentication.service.regionService;


import com.authentication.model.region.City;

import java.util.List;

public interface CityService {




    List<City> createOrUpdateCities(List<String> citiesName,Long stateId);

    void deleteCity(Long id);

    List<City> getAllCitiesByStateId(Long stateId);
}
