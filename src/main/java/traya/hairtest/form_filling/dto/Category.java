package traya.hairtest.form_filling.dto;

import lombok.Data;

import java.util.List;

@Data
public class Category {
    private String name;
    private List<Question> questions;
}

