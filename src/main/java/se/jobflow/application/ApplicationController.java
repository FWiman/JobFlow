package se.jobflow.application;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jobflow.application.dto.ApplicationResponse;
import se.jobflow.application.dto.CreateApplicationRequest;
import se.jobflow.application.dto.UpdateApplicationStatusRequest;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }


    @PostMapping
    public ResponseEntity<ApplicationResponse> createApplication(
            @Valid
            @RequestBody
            CreateApplicationRequest request) {

        ApplicationResponse application = applicationService.createApplication(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(application);
    }

    @GetMapping
    public ResponseEntity<List<ApplicationResponse>> getAllApplications() {
        List<ApplicationResponse> allApplications = applicationService.getAllApplications();

        return ResponseEntity.ok(allApplications);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponse> getApplicationById(
            @PathVariable
            long id) {
        return applicationService.getApplicationById(id)
                .map(application -> ResponseEntity.ok(application))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PatchMapping("/{id}/status")
    public ResponseEntity<ApplicationResponse> updateApplication(
            @PathVariable long id,
            @Valid
            @RequestBody UpdateApplicationStatusRequest request) {
        return applicationService.updateApplicationStatus(id, request)
                .map(application -> ResponseEntity.ok(application))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(
            @PathVariable
            long id) {
        boolean deleted = applicationService.deleteApplication(id);

        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
