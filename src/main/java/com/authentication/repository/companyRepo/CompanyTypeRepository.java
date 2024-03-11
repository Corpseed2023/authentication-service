package com.authentication.repository.companyRepo;



import com.authentication.model.companyModel.CompanyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyTypeRepository extends JpaRepository<CompanyType,Long> {

    @Query("SELECT c.companyTypeName FROM CompanyType c")
    List<String> findAllCompanyTypeNames();
}
