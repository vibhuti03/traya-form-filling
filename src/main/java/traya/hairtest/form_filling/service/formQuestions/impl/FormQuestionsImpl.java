package traya.hairtest.form_filling.service.formQuestions.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import traya.hairtest.form_filling.dto.Questionnare;
import traya.hairtest.form_filling.dto.request.FormQuestionRequestDto;
import traya.hairtest.form_filling.dto.response.FormQuestionResponseDto;
import traya.hairtest.form_filling.service.formQuestions.FormQuestions;
import traya.hairtest.form_filling.utils.QuestionnareLoader;

@Service
@AllArgsConstructor
public class FormQuestionsImpl implements FormQuestions {

    @Autowired
    private QuestionnareLoader questionnareLoader;

    @Override
    public FormQuestionResponseDto getNextFormQuestion(FormQuestionRequestDto request) {
        Questionnare questionnare = questionnareLoader.get(request.getGender());

        if(questionnare==null){
            throw new RuntimeException("Invalid Gender Question Requested");
        }
        return questionnare.getQuestions().stream()
                .filter(question -> question.getNumber() == request.getNextQuestionNumber())
                .findFirst()
                .map(question -> FormQuestionResponseDto.builder()
                        .questionNumber(question.getNumber())
                        .category(question.getCategory())
                        .type(question.getType())
                        .question(question.getText())
                        .options(question.getOptions())
                        .build())
                .orElse(FormQuestionResponseDto.builder()
                        .completed(true)
                        .build());
    }
}
