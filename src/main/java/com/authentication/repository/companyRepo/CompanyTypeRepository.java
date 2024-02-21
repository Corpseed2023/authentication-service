package com.authentication.repository.companyRepo;



import com.authentication.model.companyModel.CompanyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyTypeRepository extends JpaRepository<CompanyType,Long> {
}
