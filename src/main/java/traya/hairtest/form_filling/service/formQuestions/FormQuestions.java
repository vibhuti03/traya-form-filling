package traya.hairtest.form_filling.service.formQuestions;

import traya.hairtest.form_filling.dto.request.FormQuestionRequestDto;
import traya.hairtest.form_filling.dto.response.FormQuestionResponseDto;

public interface FormQuestions {
    public FormQuestionResponseDto getNextFormQuestion(FormQuestionRequestDto request);
}
