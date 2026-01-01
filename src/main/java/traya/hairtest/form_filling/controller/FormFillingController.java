package traya.hairtest.form_filling.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import traya.hairtest.form_filling.dto.request.UserDemographicRequestDto;
import traya.hairtest.form_filling.dto.request.UserFormStatusRequestDto;
import traya.hairtest.form_filling.dto.response.UserDemographicResponseDto;
import traya.hairtest.form_filling.dto.response.UserFormStatusResponseDto;
import traya.hairtest.form_filling.service.userDemographicDetailsSaveOrUpdate.UserDemographicDetails;
import traya.hairtest.form_filling.service.userFormStatus.UserFormStatus;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class FormFillingController {

    @Autowired
    private UserFormStatus userFormStatus;

    @Autowired
    private UserDemographicDetails userDemographicDetails;

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck(@RequestParam String name){
        return ResponseEntity.ok().body("Hello "+name);
    }

    @PostMapping("/user-form-status")
    public UserFormStatusResponseDto userFormStatus(
            @Valid
            @RequestBody
            UserFormStatusRequestDto request
    ){
        return userFormStatus.getUserFormStatus(request.getPhone());
    }

    @PostMapping("/user-form-demographics")
    public UserDemographicResponseDto userDemographics(
            @Valid
            @RequestBody
            UserDemographicRequestDto request
    ){
        return userDemographicDetails.saveOrUpdateuserDemographicDetails(request);
    }
}
