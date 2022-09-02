package pl.tchorzyksen.my.web.service.controller;

import java.util.HashSet;
import java.util.Set;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.tchorzyksen.my.web.service.entities.BusinessUnitEntity;
import pl.tchorzyksen.my.web.service.model.request.BusinessUnitRequest;
import pl.tchorzyksen.my.web.service.model.response.BusinessUnitResponse;
import pl.tchorzyksen.my.web.service.repositories.BusinessUnitRepository;

@RestController
@RequestMapping("/business-unit")
public class BusinessUnitController {

  @Autowired
  private BusinessUnitRepository businessUnitRepository;

  private final ModelMapper modelMapper = new ModelMapper();

  @PostMapping
  public ResponseEntity<BusinessUnitResponse> createBusinessUnit(@RequestBody BusinessUnitRequest businessUnitRequest) {

    BusinessUnitEntity businessUnit = modelMapper.map(businessUnitRequest, BusinessUnitEntity.class);

    BusinessUnitResponse businessUnitResponse = modelMapper.map(businessUnitRepository.save(businessUnit), BusinessUnitResponse.class);

    return ResponseEntity.status(HttpStatus.CREATED).body(businessUnitResponse);
  }

  @GetMapping
  public ResponseEntity<Set<BusinessUnitResponse>> getBusinessUnits() {

    Set<BusinessUnitResponse> businessUnitResponses = new HashSet<>();

    businessUnitRepository.findAll().forEach(businessUnitEntity ->
        businessUnitResponses.add(modelMapper.map(businessUnitEntity, BusinessUnitResponse.class)));

    return ResponseEntity.ok(businessUnitResponses);
  }
}
