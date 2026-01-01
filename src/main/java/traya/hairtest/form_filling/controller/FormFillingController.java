package traya.hairtest.form_filling.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import traya.hairtest.form_filling.dto.request.*;
import traya.hairtest.form_filling.dto.response.*;
import traya.hairtest.form_filling.service.formQuestions.FormQuestions;
import traya.hairtest.form_filling.service.submitAnswers.SubmitAnswers;
import traya.hairtest.form_filling.service.submitForm.SubmitForm;
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

    @Autowired
    private FormQuestions formQuestions;

    @Autowired
    private SubmitAnswers submitAnswers;

    @Autowired
    private SubmitForm submitForm;

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

    @PostMapping("/next-question")
    public FormQuestionResponseDto getNextFormQuestion(
            @Valid
            @RequestBody
            FormQuestionRequestDto request
    ){
        return formQuestions.getNextFormQuestion(request);
    }

    @PostMapping("/submit-answer")
    public SubmitAnswerResponseDto submitAnswer(
            @Valid @RequestBody SubmitAnswerRequestDto request
    ) {
        return submitAnswers.submitAnswer(request);
    }

    @PostMapping("/submit-form")
    public SubmitFormResponseDto submitForm(
            @Valid @RequestBody SubmitFormRequestDto request
    ) {
        return submitForm.submitForm(request);
    }
}
