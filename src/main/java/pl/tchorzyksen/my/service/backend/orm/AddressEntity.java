package pl.tchorzyksen.my.service.backend.orm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.tchorzyksen.my.service.backend.model.Country;

@Getter
@Setter
@ToString(callSuper = true)
@RequiredArgsConstructor
@Entity(name = "addresses")
public class AddressEntity extends AbstractEntity<Long> {

  @Column(name = "street")
  private String street;

  @Column(name = "postal_code")
  private String postalCode;

  @Column(name = "city")
  private String city;

  @Column(name = "state")
  private String state;

  @Enumerated(EnumType.STRING)
  @Column(name = "country")
  private Country countryCode;

}
