package com.example.apicrud.jobOffer;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
class JobOfferService {
    private final JobOfferRepository jobOfferRepository;
    private final JobOfferDtoMapper jobOfferDtoMapper;

    public JobOfferService(JobOfferRepository jobOfferRepository, JobOfferDtoMapper jobOfferDtoMapper) {
        this.jobOfferRepository = jobOfferRepository;
        this.jobOfferDtoMapper = jobOfferDtoMapper;
    }


    Optional<JobOfferDto> getOfferById(Long id){
        return jobOfferRepository.findById(id).map(jobOfferDtoMapper::map);
    }

    JobOfferDto saveOffer(JobOfferDto jobOfferDto){
        JobOffer jobOfferToSave = jobOfferDtoMapper.map(jobOfferDto);
        jobOfferToSave.setDateAdded(LocalDateTime.now());
        JobOffer savedJobOffer = jobOfferRepository.save(jobOfferToSave);
        return jobOfferDtoMapper.map(savedJobOffer);
    }

    Optional<JobOfferDto> replaceJobOffer(Long offerId, JobOfferDto jobOfferDto){
        if (!jobOfferRepository.existsById(offerId)){
            return Optional.empty();
        }
        jobOfferDto.setId(offerId);
        JobOffer jobOfferToUpdate = jobOfferDtoMapper.map(jobOfferDto);
        JobOffer updatedJobOffer = jobOfferRepository.save(jobOfferToUpdate);
        return Optional.of(jobOfferDtoMapper.map(updatedJobOffer));
    }

    void updateJobOffer(JobOfferDto jobOfferDto){
        JobOffer jobOffer = jobOfferDtoMapper.map(jobOfferDto);
        jobOfferRepository.save(jobOffer);
    }

    void deleteJobOffer(Long id){
        jobOfferRepository.deleteById(id);
    }
}
