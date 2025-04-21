package ru.ibs.trainings.spring.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.springframework.validation.annotation.Validated;

@Builder
@Validated
@Jacksonized
@JsonPropertyOrder({"name", "codeName"})
public record CountryDto(
    @Size(min = 2, max = 255) String codeName,
    @Pattern(regexp = "\\s+", message = "") String name) {
}
