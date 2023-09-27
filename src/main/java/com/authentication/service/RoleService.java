package com.authentication.service;

import com.authentication.model.Roles;
import com.authentication.payload.response.ResponseEntity;

import java.util.List;

public interface RoleService {
    ResponseEntity<List<Roles>> fetchAllRoles();
}
