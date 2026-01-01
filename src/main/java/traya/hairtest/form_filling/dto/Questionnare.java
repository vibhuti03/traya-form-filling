package traya.hairtest.form_filling.dto;

import lombok.Data;

import java.util.List;

@Data
public class Questionnare {
    private String version;
    private String gender;
    private List<Question> questions;
}

