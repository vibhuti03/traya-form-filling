package traya.hairtest.form_filling.dto.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubmitFormResponseDto {
    private boolean submitted;
}
