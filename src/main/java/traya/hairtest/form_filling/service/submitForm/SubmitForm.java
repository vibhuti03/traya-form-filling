package traya.hairtest.form_filling.service.submitForm;

import traya.hairtest.form_filling.dto.request.SubmitFormRequestDto;
import traya.hairtest.form_filling.dto.response.SubmitFormResponseDto;

public interface SubmitForm {
    public SubmitFormResponseDto submitForm(SubmitFormRequestDto request);
}
