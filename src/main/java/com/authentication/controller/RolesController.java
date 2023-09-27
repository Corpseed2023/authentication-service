package com.authentication.controller;

import com.authentication.model.Roles;
import com.authentication.payload.response.ResponseEntity;
import com.authentication.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api("Handle role related actions")
@RequestMapping("/api/auth/role")
public class RolesController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "Return all roles",
            notes = "Return all roles as list",response = Roles.class,responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "Successfully token generated"),
            @ApiResponse(code = 500,message = "Something Went-Wrong"),
            @ApiResponse(code = 400,message = "Bad Request")
    })
    @GetMapping()
    public ResponseEntity<List<Roles>> fetchRoles(){
        return this.roleService.fetchAllRoles();
    }
}
