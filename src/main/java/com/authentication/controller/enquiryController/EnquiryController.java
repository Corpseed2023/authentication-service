package com.authentication.controller.enquiryController;

import com.authentication.model.Enquiry;
import com.authentication.payload.response.ResponseEntity;
import com.authentication.service.EnquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/createEnquiry")

public class EnquiryController {
    @Autowired
    private EnquiryService enquiryService;
    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public Enquiry createEnquiry(@RequestBody Enquiry enquiry)
    {
        return  enquiryService.createEnquiry(enquiry);

    }

}
