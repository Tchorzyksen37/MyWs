package pl.tchorzyksen.my.web.service.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.tchorzyksen.my.web.service.model.Country;

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
