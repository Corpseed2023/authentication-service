package com.authentication.repository.regionRepo;



import com.authentication.model.region.States;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StatesRepository extends JpaRepository<States,Long>

{

    States findByStateName(String stateName);

    List<States> findByIsEnable(boolean b);
}
