package com.authentication.controller;

import com.authentication.payload.request.UserRequest;
import com.authentication.payload.response.ResponseEntity;
import com.authentication.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("Handle User related actions")
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Return signup result",response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "Successfully registered"),
            @ApiResponse(code = 500,message = "Something Went-Wrong"),
            @ApiResponse(code = 400,message = "Bad Request")
    })
    @PostMapping("/save")
    public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest){
        return this.userService.createUser(userRequest);
    }
}
