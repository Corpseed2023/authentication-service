package com.authentication.controller.regionController;


import com.authentication.model.region.City;
import com.authentication.service.regionService.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companyServices/company/city")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping
    public ResponseEntity<List<City>> getAllStates() {
        List<City> allCities = cityService.getAllCities();
        return new ResponseEntity<>(allCities, HttpStatus.OK);
    }
    @PostMapping("/saveCities")
    public ResponseEntity<List<City>> createOrUpdateCities(@RequestBody List<String> citiesName, @RequestParam Long stateId) {
        List<City> savedCities = cityService.createOrUpdateCities(citiesName, stateId);
        return new ResponseEntity<>(savedCities, HttpStatus.CREATED);
    }

    @DeleteMapping("/removeState")
    public ResponseEntity<Void> deleteState(@PathVariable Long id) {
        cityService.deleteCity(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
