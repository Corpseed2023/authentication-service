package com.authentication.serviceImpl;


import com.authentication.model.Enquiry;
import com.authentication.repository.EnquiryRepo;
import com.authentication.service.EnquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnquiryServiceImpl implements EnquiryService {

    @Autowired
    private EnquiryRepo enquiryRepo;
    @Override
    public Enquiry createEnquiry(Enquiry enquiry) {

        Enquiry enquiry1 = this.enquiryRepo.findByMobile(enquiry.getMobile());

        if (enquiry1!=null)
        {
            enquiry1.setCount(enquiry1.getCount()+1);
            return enquiryRepo.save(enquiry1);
        }
       else
        {
            return enquiryRepo.save(enquiry);
        }

    }
}
