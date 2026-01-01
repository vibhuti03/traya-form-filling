package traya.hairtest.form_filling.dto;

import lombok.Data;

import java.util.List;

@Data
public class Question {
    private int number;
    private String category;
    private String type; // SCQ / MCQ
    private String text;
    private List<String> options;
}

