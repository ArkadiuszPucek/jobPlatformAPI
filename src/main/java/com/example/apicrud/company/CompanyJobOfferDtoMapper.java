package com.example.apicrud.company;

import com.example.apicrud.jobOffer.JobOffer;
import org.springframework.stereotype.Service;

@Service
class CompanyJobOfferDtoMapper {

    CompanyJobOfferDto map(JobOffer jobOffer){
        CompanyJobOfferDto dto = new CompanyJobOfferDto();
        dto.setId(jobOffer.getId());
        dto.setTitle(jobOffer.getTitle());
        dto.setMinSalary(jobOffer.getMinSalary());
        dto.setMaxSalary(jobOffer.getMaxSalary());
        dto.setLocation(jobOffer.getLocation());
        return dto;
    }
}
