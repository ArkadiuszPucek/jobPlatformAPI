package com.example.apicrud.company;

import org.springframework.stereotype.Service;

@Service
class CompanyDtoMapper {

    CompanyDto map(Company company){
        CompanyDto dto = new CompanyDto();
        dto.setId(company.getId());
        dto.setName(company.getName());
        dto.setDescription(company.getDescription());
        dto.setCity(company.getCity());
        dto.setEmployees(company.getEmployees());
        dto.setTelephone(company.getTelephone());
        dto.setEmail(company.getEmail());
        return dto;
    }

    Company map(CompanyDto companyDto){
        Company company = new Company();
        company.setId(companyDto.getId());
        company.setName(companyDto.getName());
        company.setDescription(companyDto.getDescription());
        company.setCity(companyDto.getCity());
        company.setEmployees(companyDto.getEmployees());
        company.setTelephone(companyDto.getTelephone());
        company.setEmail(companyDto.getEmail());
        return company;
    }
}
