package pl.tchorzyksen.my.web.service.service;

import pl.tchorzyksen.my.web.service.model.dto.BusinessUnitDto;

public interface BusinessUnitService {

  BusinessUnitDto getBusinessUnitById(Long businessUnitId);

}
