package com.authentication.serviceImpl;

import com.authentication.model.Roles;
import com.authentication.payload.response.MessageResponse;
import com.authentication.payload.response.ResponseEntity;
import com.authentication.repository.RoleRepository;
import com.authentication.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public ResponseEntity<List<Roles>> fetchAllRoles() {
        return new ResponseEntity<List<Roles>>().ok(this.roleRepository.findAll());
    }

    @Override
    public ResponseEntity<Roles> createRole(@RequestBody Roles role) {
        // Check if the role name is provided and not empty
        if (role.getRole() == null || role.getRole().isEmpty()) {
            // Handle validation for role name (ensure it's not null or empty)
            return new ResponseEntity<List<Roles>>().ok();
        }

        // Set the created and updated timestamps
        role.setCreatedAt(new Date());
        role.setUpdatedAt(new Date());

        // Save the role to the database
        Roles savedRole = roleRepository.save(role);

        // Return a ResponseEntity with status 201 (created) and include the saved role in the response body
        return new ResponseEntity<MessageResponse>().ok(new MessageResponse("Role Successfully Created"));
    }

    @Override
    public ResponseEntity<Void> deleteRole(Long roleId) {
        roleRepository.deleteById(roleId);
        return new ResponseEntity<MessageResponse>().ok(new MessageResponse("Deleted"));
    }
}
