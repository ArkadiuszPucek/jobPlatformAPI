package com.example.apicrud.company;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/companies")
class CompanyController {
    private final CompanyService companyService;
    private final ObjectMapper objectMapper;

    public CompanyController(CompanyService companyService, ObjectMapper objectMapper) {
        this.companyService = companyService;
        this.objectMapper = objectMapper;
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
        URI savedCompanyUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id")
                .buildAndExpand(savedCompany.getId())
                .toUri();
        return ResponseEntity.created(savedCompanyUri).body(savedCompany);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> replaceCompany(@PathVariable Long id,@RequestBody CompanyDto companyDto){
        return companyService.replaceCompany(id,companyDto)
                .map(c ->ResponseEntity.noContent().build())
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    ResponseEntity<?> updateCompany(@PathVariable Long id, @RequestBody JsonMergePatch patch){
        try {
            CompanyDto company = companyService.getCompanyById(id).orElseThrow();
            CompanyDto comapnyPached = applyPatch(company, patch);
            companyService.updateJobOffer(comapnyPached);
        }catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.noContent().build();
    }

    private CompanyDto applyPatch(CompanyDto company, JsonMergePatch patch) throws JsonPatchException, JsonProcessingException {
        JsonNode companyNode = objectMapper.valueToTree(company);
        JsonNode companyPatchNode = patch.apply(companyNode);
        return objectMapper.treeToValue(companyPatchNode, CompanyDto.class);
    }
}
