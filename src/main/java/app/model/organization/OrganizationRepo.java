package app.model.organization;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepo extends JpaRepository<Organization, Integer> {


    List<Organization> findAllByCategory_NameEn(String nameEn);

}
