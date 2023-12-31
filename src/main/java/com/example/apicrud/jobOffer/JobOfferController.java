package com.example.apicrud.jobOffer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.HttpRetryException;
import java.net.URI;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/offers")
class JobOfferController {

    private final JobOfferService jobOfferService;
    private final ObjectMapper objectMapper;

    public JobOfferController(JobOfferService jobOfferService, ObjectMapper objectMapper) {
        this.jobOfferService = jobOfferService;
        this.objectMapper = objectMapper;
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

    @PatchMapping("/{id}")
    ResponseEntity<?> updateJobOffer(@PathVariable Long id,@RequestBody JsonMergePatch patch){
        try {
            JobOfferDto jobOffer = jobOfferService.getOfferById(id).orElseThrow();
            JobOfferDto offerPatched = applyPatch(jobOffer, patch);
            jobOfferService.updateJobOffer(offerPatched);
        }catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.noContent().build();
    }

    private JobOfferDto applyPatch(JobOfferDto jobOffer, JsonMergePatch patch) throws JsonPatchException, JsonProcessingException {
        JsonNode jobOfferNode = objectMapper.valueToTree(jobOffer);
        JsonNode jobOfferPatchNode = patch.apply(jobOfferNode);
        return objectMapper.treeToValue(jobOfferPatchNode, JobOfferDto.class);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteJobOffer(@PathVariable Long id){
        jobOfferService.deleteJobOffer(id);
        return ResponseEntity.noContent().build();
    }


}
