package com.authentication.controller.roleController;

import com.authentication.model.Roles;
import com.authentication.payload.response.ResponseEntity;
import com.authentication.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api("Handle role related actions")
@RequestMapping("/api/auth/role")
public class RolesController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "Return all roles",
            notes = "Return all roles as list", response = Roles.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully token generated"),
            @ApiResponse(code = 500, message = "Something Went-Wrong"),
            @ApiResponse(code = 400, message = "Bad Request")
    })

    @GetMapping("getAllRoles")
    public ResponseEntity<List<Roles>> fetchRoles() {
        return this.roleService.fetchAllRoles();
    }

    @ApiOperation(value = "Create a new role",
            notes = "Create a new role", response = Roles.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Role created successfully"),
            @ApiResponse(code = 500, message = "Something Went Wrong"),
            @ApiResponse(code = 400, message = "Bad Request")
    })

    @PostMapping("/createRole")
    public ResponseEntity<Roles> createRole(@RequestBody Roles role) {
        return roleService.createRole(role);
    }

    @ApiOperation(value = "Delete a role by ID",
            notes = "Delete a role by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Role deleted successfully"),
            @ApiResponse(code = 500, message = "Something Went Wrong"),
            @ApiResponse(code = 400, message = "Bad Request")
    })
    @DeleteMapping("/removeRole")
    public ResponseEntity<Void> deleteRole(@PathVariable Long roleId) {
        return roleService.deleteRole(roleId);
    }


}