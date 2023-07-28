package pl.tchorzyksen.my.service.backend.service.impl;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tchorzyksen.my.service.backend.exception.ResourceNotFoundException;
import pl.tchorzyksen.my.service.backend.model.dto.BusinessUnitDto;
import pl.tchorzyksen.my.service.backend.repositories.BusinessUnitRepository;
import pl.tchorzyksen.my.service.backend.orm.BusinessUnitEntity;
import pl.tchorzyksen.my.service.backend.service.BusinessUnitService;

@Slf4j
@Service
public class BusinessUnitServiceImpl implements BusinessUnitService {

  public static final String RESOURCE_NAME = "businessUnit";

  @Autowired
  private BusinessUnitRepository businessUnitRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Override
  public BusinessUnitDto getBusinessUnitById(@NonNull Long businessUnitId) {
    log.debug("Fetch businessUnit with id: {}", businessUnitId);
    return businessUnitRepository.findById(businessUnitId).map(this::mapToDto)
        .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, businessUnitId));

  }

  private BusinessUnitDto mapToDto(BusinessUnitEntity businessUnitEntity) {
    return modelMapper.map(businessUnitEntity, BusinessUnitDto.class);
  }

}
