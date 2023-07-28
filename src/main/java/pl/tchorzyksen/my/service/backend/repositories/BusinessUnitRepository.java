package pl.tchorzyksen.my.service.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.tchorzyksen.my.service.backend.orm.BusinessUnitEntity;

@Repository
public interface BusinessUnitRepository extends CrudRepository<BusinessUnitEntity, Long> {
}
