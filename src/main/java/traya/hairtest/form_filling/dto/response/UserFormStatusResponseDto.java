package traya.hairtest.form_filling.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import traya.hairtest.form_filling.constants.Gender;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserFormStatusResponseDto {

    private boolean userExists;

    private String name;
    private Gender gender;

    private Boolean formSubmitted;
    private LocalDateTime lastSubmitted;
    private Integer nextQuestionIndex;
}
