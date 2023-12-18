//package com.authentication.serviceImpl;
//
//import com.authentication.model.SignupUser;
//import com.authentication.repository.SignupRepo;
//import com.authentication.service.SignupService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class SignupImpl implements SignupService {
//
//    @Autowired
//    private SignupRepo signupRepo;
//
//    @Override
//    public SignupUser createUserDetail(SignupUser signupUser) {
//
//        SignupUser signupUser1= new SignupUser();
//        return this.signupRepo.save(signupUser);
//
//    }
//
//}
