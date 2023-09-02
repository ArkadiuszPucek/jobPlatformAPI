package com.example.apicrud.company;

import com.example.apicrud.jobOffer.JobOffer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String city;
    private Integer employees;
    private String telephone;
    private String email;
    @OneToMany(mappedBy = "company", cascade = CascadeType.REMOVE)
    private List<JobOffer> jobOfferList = new ArrayList<>();


}
