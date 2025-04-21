package ru.ibs.trainings.spring.advanced.impl.mappers;

import lombok.Setter;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ibs.trainings.spring.advanced.impl.model.Country;
import ru.ibs.trainings.spring.dto.CountryDto;

//@UtilityClass
@Component
@SuppressWarnings("java:S1118")
public class CountryMapper {

  @Setter(onMethod_ = @Autowired)
  @NonFinal static String s;

  @SuppressWarnings("unused")
  public static String m() {
    return s;
  }

  public static CountryDto toCountryDto(Country country) {
    return CountryDto.builder()
                     .codeName(country.getCodeName())
                     .name(country.getName())
                     .build();
  }

  public static Country toCountryEntity(CountryDto countryDto) {
    return new Country(countryDto.codeName(),
                       countryDto.name());
  }
}
