package com.authentication.repository.companyRepo;



import com.authentication.model.companyModel.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {

    Company findByIdAndUserId(Long companyId, Long userId);


    List<Company> findAllByUserId(Long userId);

    @Query("SELECT c.companyName FROM Company c")
    List<String> findAllCompanyNames();

    List<Company> findByUserId(Long userId);
}
