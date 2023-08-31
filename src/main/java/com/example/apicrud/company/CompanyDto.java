package com.example.apicrud.company;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
class CompanyDto {
    private Long id;
    private String name;
    private String description;
    private String city;
    private Integer employees;
    private String telephone;
    private String email;
}
