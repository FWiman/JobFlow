package se.jobflow.application.dto;

import se.jobflow.application.ApplicationStatus;

import java.time.LocalDate;

public record ApplicationResponse(
        Long id,
        String companyName,
        String position,
        ApplicationStatus status,
        LocalDate appliedDate
) {
}
