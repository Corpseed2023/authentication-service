package com.authentication.serviceImpl;

import com.authentication.model.Roles;
import com.authentication.payload.response.ResponseEntity;
import com.authentication.repository.RoleRepository;
import com.authentication.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public ResponseEntity<List<Roles>> fetchAllRoles() {
        return new ResponseEntity<List<Roles>>().ok(this.roleRepository.findAll());
    }
}
