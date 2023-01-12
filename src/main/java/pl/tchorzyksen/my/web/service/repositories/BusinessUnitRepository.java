package pl.tchorzyksen.my.web.service.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.tchorzyksen.my.web.service.orm.BusinessUnitEntity;

@Repository
public interface BusinessUnitRepository extends CrudRepository<BusinessUnitEntity, Long> {
}
