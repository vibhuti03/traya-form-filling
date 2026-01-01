package traya.hairtest.form_filling.dto.response;

import lombok.Builder;
import lombok.Data;
import traya.hairtest.form_filling.constants.Gender;

@Data
@Builder
public class UserFormStatusResponseDto {

    private boolean userExists;

    private Boolean formSubmitted;

    private Integer currentQuestionIndex;
    private String name;
    private Integer age;
    private Gender gender;
    private String location;
}
