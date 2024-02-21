package com.authentication.serviceImpl.regionLogic;


import com.authentication.model.region.States;
import com.authentication.repository.regionRepo.StatesRepository;
import com.authentication.service.regionService.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateServiceImpl implements StateService {

    @Autowired
    private StatesRepository statesRepository;

    @Override
    public States createStates(States states) {
        return statesRepository.save(states);
    }

    @Override
    public List<States> getAllStates() {
        return statesRepository.findAll();
    }

    @Override
    public States updateState(Long id, States states) {
        States existingState = statesRepository.findById(id).orElse(null);

        if (existingState != null) {
            // Update fields as needed
            existingState.setStateName(states.getStateName());
            existingState.setUpdatedAt(states.getUpdatedAt());
            existingState.setEnable(states.isEnable());

            // Save the updated state
            return statesRepository.save(existingState);
        } else {
            return null; // Handle not found scenario
        }
    }



}
