package com.example.apicrud.company;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/companies")
class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/{id}")
    ResponseEntity<CompanyDto> getCompanyById(@PathVariable Long id){
        return companyService.getCompanyById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/offers")
    ResponseEntity<List<CompanyJobOfferDto>> getCompanyOffers(@PathVariable Long id){
        if(companyService.getCompanyById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok(companyService.getJobOffersByCompanyId(id));
        }
    }

    @PostMapping("")
    ResponseEntity<CompanyDto> saveCompany(@RequestBody CompanyDto company){
        CompanyDto savedCompany = companyService.saveCompany(company);
        URI savedComapnyUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id")
                .buildAndExpand(savedCompany.getId())
                .toUri();
        return ResponseEntity.created(savedComapnyUri).body(savedCompany);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> replaceCompany(@PathVariable Long id,@RequestBody CompanyDto companyDto){
        return companyService.replaceCompany(id,companyDto)
                .map(c ->ResponseEntity.noContent().build())
                .orElse(ResponseEntity.notFound().build());
    }
}
