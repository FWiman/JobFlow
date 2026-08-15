package se.jobflow.application;

import org.springframework.stereotype.Service;
import se.jobflow.application.dto.ApplicationResponse;
import se.jobflow.application.dto.CreateApplicationRequest;
import se.jobflow.application.dto.UpdateApplicationStatusRequest;

import java.util.List;
import java.util.Optional;


@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }


    public ApplicationResponse createApplication(CreateApplicationRequest request) {
        Application application = new Application(
                request.companyName(),
                request.position(),
                request.status(),
                request.appliedDate());

        Application createdApplication = applicationRepository.save(application);

        return toResponse(createdApplication);
    }

    public List<ApplicationResponse> getAllApplications() {
        return applicationRepository
                .findAll()
                .stream()
                .map(application -> toResponse(application))
                .toList();
    }

    public Optional<ApplicationResponse> getApplicationById(Long id) {
        return applicationRepository
                .findById(id)
                .map(application -> toResponse(application));
    }

    public Optional<ApplicationResponse> updateApplicationStatus(
            Long id,
            UpdateApplicationStatusRequest request
    ) {
        return applicationRepository
                .findById(id)
                .map(application -> {
                    application.updateStatus(request.status());

                    Application updatedApplication = applicationRepository.save(application);

                    return toResponse(updatedApplication);
                });
    }

    public boolean deleteApplication(Long id) {
        boolean exists = applicationRepository.existsById(id);

        if (exists) {
            applicationRepository.deleteById(id);
            return true;
        }
        return false;

    }


    private ApplicationResponse toResponse(Application application) {
        return new ApplicationResponse(
                application.getId(),
                application.getCompanyName(),
                application.getPosition(),
                application.getStatus(),
                application.getAppliedDate()
        );
    }

}
