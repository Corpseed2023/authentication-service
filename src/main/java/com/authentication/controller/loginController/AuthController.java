package com.authentication.controller.loginController;

import com.authentication.payload.request.TokenRequest;
import com.authentication.payload.response.JwtResponse;
import com.authentication.payload.response.ResponseEntity;
import com.authentication.service.JwtTokenProviderService;
import com.authentication.serviceImpl.OtpServiceImpl;
import com.authentication.serviceImpl.UserDetailsImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Api("Generate Token")
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProviderService jwtTokenProviderService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private OtpServiceImpl otpServiceImpl;

    @ApiOperation(value = "generate token",
            notes = "Return generated token", response = ResponseEntity.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully token generated"),
            @ApiResponse(code = 500, message = "Something Went-Wrong"),
            @ApiResponse(code = 400, message = "Bad Request")
    })
    @PostMapping("/token")
    public ResponseEntity<?> generateToken(@RequestBody TokenRequest tokenRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(tokenRequest.getUsername(), tokenRequest.getPassword()));


        System.out.println(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = this.jwtTokenProviderService.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());


        List<String> ipaddressdetails = getSystemIPAddress();

        System.out.println(ipaddressdetails.size());
        otpServiceImpl.sendLoginUserDetails(tokenRequest.getUsername(),authentication.getName(),ipaddressdetails.get(0),
                ipaddressdetails.get(1),ipaddressdetails.get(2));

        return new ResponseEntity().ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles,
                userDetails.getAssociated(),userDetails.getSubscribed()));
    }




//    @GetMapping("/logout")
//    public String fetchSignoutSite(HttpServletRequest request, HttpServletResponse response){
//
//        HttpSession session = request.getSession(false);
//        SecurityContextHolder.clearContext();
//
//        session = request.getSession(false);
//        if(session != null) {
//            session.invalidate();
//        }
//
//        for(Cookie cookie : request.getCookies()) {
//            cookie.setMaxAge(0);
//        }
//
//        return "Logout Successfully Done";
//    }


    @GetMapping("/logout")
    public String fetchSignoutSite(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "Logout Successfully Done";
    }


    private List<String> getSystemIPAddress() {

        try {

            InetAddress localhost = InetAddress.getLocalHost();

            // Get system IP address
            // Get system name (hostname)
            String systemName = localhost.getHostName();
            String networkIPAddress = Arrays.toString(localhost.getAddress());

            String systemIPAddress = localhost.getHostAddress();

            List<String>  ipaddressdetails= new ArrayList<>();
            ipaddressdetails.add(systemName);
            ipaddressdetails.add(networkIPAddress);
            ipaddressdetails.add(systemIPAddress);


            return ipaddressdetails; // return the IP address as a String
        } catch (UnknownHostException e) {
            System.err.println("Error resolving host: " + e.getMessage());
            return new ArrayList<>(); // or any other default value
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(); // handle other exceptions appropriately in your application
        }
    }


}