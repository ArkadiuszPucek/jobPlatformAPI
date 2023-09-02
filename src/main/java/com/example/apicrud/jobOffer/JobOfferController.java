package com.example.apicrud.jobOffer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/offers")
class JobOfferController {

    private final JobOfferService jobOfferService;

    public JobOfferController(JobOfferService jobOfferService) {
        this.jobOfferService = jobOfferService;
    }


    @GetMapping("/{id}")
    ResponseEntity<JobOfferDto> getOfferedById(@PathVariable Long id){
        return jobOfferService.getOfferById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    ResponseEntity<JobOfferDto> saveOffer(@RequestBody JobOfferDto jobOfferDto){
        JobOfferDto savedJobOffer = jobOfferService.saveOffer(jobOfferDto);
        URI savedJobOfferUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedJobOffer.getId())
                .toUri();
        return ResponseEntity.created(savedJobOfferUri).body(savedJobOffer);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> replaceJobOffer (@PathVariable Long id,@RequestBody JobOfferDto jobOfferDto){
        return jobOfferService.replaceJobOffer(id,jobOfferDto)
                .map(c ->ResponseEntity.noContent().build())
                .orElse(ResponseEntity.notFound().build());
    }


}
