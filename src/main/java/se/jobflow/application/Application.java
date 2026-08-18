package se.jobflow.application;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import se.jobflow.application.exception.InvalidApplicationStatusException;

import java.time.LocalDate;

@Entity
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String companyName;

    @NotBlank
    private String position;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @NotNull
    private LocalDate appliedDate;


    protected  Application() {
    }

    public Application(
            String companyName,
            String position,
            ApplicationStatus status,
            LocalDate appliedDate) {

        this.companyName = companyName;
        this.position = position;
        this.status = status;
        this.appliedDate = appliedDate;
    }

    private boolean isValidStatusTransition(ApplicationStatus newStatus) {
        return switch (status) {
            case SAVED ->  newStatus == ApplicationStatus.APPLIED;
            case APPLIED ->   newStatus == ApplicationStatus.INTERVIEW ||newStatus == ApplicationStatus.REJECTED;
            case INTERVIEW ->   newStatus == ApplicationStatus.OFFER || newStatus ==  ApplicationStatus.REJECTED;
            case OFFER, REJECTED -> false;
        };
    }

    public void updateStatus(ApplicationStatus newStatus) {
        if (!isValidStatusTransition(newStatus)) {
            throw new InvalidApplicationStatusException(
                    "Cannot update application status from " + status + " to " + newStatus
            );
        }
        this.status = newStatus;
    }

    public Long getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getPosition() {
        return position;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public LocalDate getAppliedDate() {
        return appliedDate;
    }


}
