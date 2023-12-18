package com.authentication.repository;

import com.authentication.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Roles,Long> {


    @Query(value = "SELECT * FROM roles r WHERE r.role IN (:rolesList)", nativeQuery = true)
    Set<Roles> findRoleList(List<String> rolesList);
    Roles findByRole(String subscriber);
}
