package traya.hairtest.form_filling.service.submitAnswers;

import traya.hairtest.form_filling.dto.request.SubmitAnswerRequestDto;
import traya.hairtest.form_filling.dto.response.SubmitAnswerResponseDto;

public interface SubmitAnswers {
    public SubmitAnswerResponseDto submitAnswer(SubmitAnswerRequestDto request);
}
