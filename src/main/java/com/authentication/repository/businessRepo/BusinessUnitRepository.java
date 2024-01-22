package com.authentication.repository.businessRepo;



import com.authentication.model.businessUnitModel.BusinessUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface BusinessUnitRepository extends JpaRepository<BusinessUnit,Long>

{
    BusinessUnit findByAddress(String address);

    List<BusinessUnit> findByCompanyId(Long companyId);

//    BusinessUnit findByBusinessUnitId(Long businessUnitId);
    Optional<BusinessUnit> findById(Long businessUnitId);

}
