package com.authentication.repository;


import com.authentication.model.Enquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Id;

@Repository
public interface EnquiryRepo extends JpaRepository<Enquiry,Long> {
    Enquiry findByMobile(String mobile);
}
