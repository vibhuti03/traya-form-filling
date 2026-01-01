package traya.hairtest.form_filling.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import traya.hairtest.form_filling.constants.Gender;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserFormStatusResponseDto {

    private boolean userExists;

    private String name;
    private Integer age;
    private Gender gender;
    private String location;

    private Boolean formSubmitted;
    private Integer currentQuestionIndex;
}
