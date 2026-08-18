package se.jobflow.application.dto;

import jakarta.validation.constraints.NotNull;
import se.jobflow.application.ApplicationStatus;

public record UpdateApplicationStatusRequest(

        @NotNull
        ApplicationStatus status
) {
}
