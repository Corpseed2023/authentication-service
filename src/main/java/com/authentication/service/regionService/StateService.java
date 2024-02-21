package com.authentication.service.regionService;


import com.authentication.model.region.States;
import java.util.List;

public interface StateService {

    States createStates(States states);

    List<States> getAllStates();



    States updateState(Long id, States states);


}
