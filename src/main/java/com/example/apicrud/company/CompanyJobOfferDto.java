package com.example.apicrud.company;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class CompanyJobOfferDto {

    private Long id;
    private String title;
    private Double minSalary;
    private Double maxSalary;
    private String location;
}
