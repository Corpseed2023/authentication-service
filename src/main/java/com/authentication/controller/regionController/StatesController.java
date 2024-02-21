package com.authentication.controller.regionController;


import com.authentication.model.region.States;
import com.authentication.service.regionService.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companyServices/company/states")
public class StatesController {

    @Autowired
    private StateService statesService;

    @GetMapping
    public ResponseEntity<List<States>> getAllStates() {
        List<States> statesList = statesService.getAllStates();
        return new ResponseEntity<>(statesList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<States> createState(@RequestBody States states) {
        States savedState = statesService.createStates(states);
        return new ResponseEntity<>(savedState, HttpStatus.CREATED);
    }

    @PutMapping("/updateStates")
    public ResponseEntity<States> updateState(@RequestParam Long id, @RequestBody States states) {
        States updatedState = statesService.updateState(id, states);
        return new ResponseEntity<>(updatedState, HttpStatus.OK);
    }

}