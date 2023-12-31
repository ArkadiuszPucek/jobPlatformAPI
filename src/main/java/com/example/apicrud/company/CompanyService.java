package com.example.apicrud.company;

import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyJobOfferDtoMapper companyJobOfferDtoMapper;
    private final CompanyDtoMapper companyDtoMapper;

    public CompanyService(CompanyRepository companyRepository, CompanyJobOfferDtoMapper companyJobOfferDtoMapper, CompanyDtoMapper companyDtoMapper) {
        this.companyRepository = companyRepository;
        this.companyJobOfferDtoMapper = companyJobOfferDtoMapper;
        this.companyDtoMapper = companyDtoMapper;
    }

    Optional<CompanyDto> getCompanyById(Long id){
        return companyRepository.findById(id).map(companyDtoMapper::map);
    }

    List<CompanyJobOfferDto> getJobOffersByCompanyId(Long companyId){
        return companyRepository.findById(companyId)
                .map(Company::getJobOfferList)
                .orElse(Collections.emptyList())
                .stream()
                .map(companyJobOfferDtoMapper::map)
                .toList();
    }

    CompanyDto saveCompany(CompanyDto companyDto){
        Company company = companyDtoMapper.map(companyDto);
        Company savedCompany = companyRepository.save(company);
        return companyDtoMapper.map(savedCompany);
    }
    
    Optional<CompanyDto> replaceCompany(Long companyId, CompanyDto companyDto){
        if(!companyRepository.existsById(companyId)){
            return Optional.empty();
        }
        companyDto.setId(companyId);
        Company companyToUpdate = companyDtoMapper.map(companyDto);
        Company updatedEntity = companyRepository.save(companyToUpdate);
        return Optional.of(companyDtoMapper.map(updatedEntity));
    }

    void updateJobOffer(CompanyDto companyDto){
        Company company = companyDtoMapper.map(companyDto);
        companyRepository.save(company);
    }

    void deleteCompany(Long id){
        companyRepository.deleteById(id);
    }

}
