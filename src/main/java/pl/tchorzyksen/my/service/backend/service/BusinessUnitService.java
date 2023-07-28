package pl.tchorzyksen.my.service.backend.service;

import pl.tchorzyksen.my.service.backend.model.dto.BusinessUnitDto;

public interface BusinessUnitService {

  BusinessUnitDto getBusinessUnitById(Long businessUnitId);

}
