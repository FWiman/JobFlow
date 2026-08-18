package se.jobflow.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import se.jobflow.application.exception.InvalidApplicationStatusException;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationTest {

    @ParameterizedTest
    @MethodSource("validStatusTransitions")
    void shouldAllowValidStatusTransition(
            ApplicationStatus currentStatus,
            ApplicationStatus newStatus
    ) {
        Application application = new Application(
                "Kambi",
                "Backend Developer",
                currentStatus,
                LocalDate.now()
        );

        application.updateStatus(newStatus);
        assertEquals(newStatus, application.getStatus());
    }

    @ParameterizedTest
    @MethodSource("invalidStatusTransitions")
    void shouldNotAllowInvalidStatusTransition(
            ApplicationStatus currentStatus,
            ApplicationStatus newStatus
    ) {
        Application application = new Application(
                "Kambi",
                "Backend Developer",
                currentStatus,
                LocalDate.now()
        );

        assertThrows(
                InvalidApplicationStatusException.class,
                () -> application.updateStatus(newStatus));
    }

    private static Stream<Arguments> validStatusTransitions() {
        return Stream.of(
                Arguments.of(ApplicationStatus.SAVED, ApplicationStatus.APPLIED),
                Arguments.of(ApplicationStatus.APPLIED, ApplicationStatus.INTERVIEW),
                Arguments.of(ApplicationStatus.APPLIED, ApplicationStatus.REJECTED),
                Arguments.of(ApplicationStatus.INTERVIEW, ApplicationStatus.OFFER),
                Arguments.of(ApplicationStatus.INTERVIEW, ApplicationStatus.REJECTED)
        );
    }

    private static Stream<Arguments> invalidStatusTransitions() {
        return Stream.of(
                Arguments.of(ApplicationStatus.APPLIED, ApplicationStatus.OFFER),
                Arguments.of(ApplicationStatus.INTERVIEW, ApplicationStatus.SAVED),
                Arguments.of(ApplicationStatus.OFFER, ApplicationStatus.APPLIED),
                Arguments.of(ApplicationStatus.REJECTED, ApplicationStatus.INTERVIEW)
        );
    }
}
