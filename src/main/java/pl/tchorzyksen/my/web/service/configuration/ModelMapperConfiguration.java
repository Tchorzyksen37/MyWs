package pl.tchorzyksen.my.web.service.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.tchorzyksen.my.web.service.entities.UserEntity;
import pl.tchorzyksen.my.web.service.model.dto.UserDto;
import pl.tchorzyksen.my.web.service.model.request.UserRequest;

@Configuration
public class ModelMapperConfiguration {

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();

    modelMapper.getConfiguration().setSkipNullEnabled(true);

    modelMapper.typeMap(UserRequest.class, UserDto.class)
        .addMappings(mapper -> mapper.skip(UserDto::setId))
        .addMappings(mapper -> mapper.skip(UserDto::setUserId));

    modelMapper.typeMap(UserEntity.class, UserDto.class)
        .addMapping(src -> src.getBusinessUnitEntity().getId(), UserDto::setBusinessUnitId);


    return modelMapper;
  }

}
