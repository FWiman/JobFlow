package se.jobflow.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import se.jobflow.application.ApplicationStatus;

import java.time.LocalDate;

public record CreateApplicationRequest(

        @NotBlank
        String companyName,

        @NotBlank
        String position,
        
        @NotNull
        ApplicationStatus status,

        @NotNull
        LocalDate appliedDate
) {
}
