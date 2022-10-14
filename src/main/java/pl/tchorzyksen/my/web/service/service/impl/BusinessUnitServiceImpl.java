package pl.tchorzyksen.my.web.service.service.impl;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tchorzyksen.my.web.service.entities.BusinessUnitEntity;
import pl.tchorzyksen.my.web.service.exceptions.ResourceNotFoundException;
import pl.tchorzyksen.my.web.service.model.dto.BusinessUnitDto;
import pl.tchorzyksen.my.web.service.repositories.BusinessUnitRepository;
import pl.tchorzyksen.my.web.service.service.BusinessUnitService;

@Slf4j
@Service
public class BusinessUnitServiceImpl implements BusinessUnitService {

  private static final String RESOURCE_NAME = "businessUnit";

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
