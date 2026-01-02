package traya.hairtest.form_filling.entity;

import jakarta.persistence.*;
import lombok.*;
import traya.hairtest.form_filling.constants.Gender;

import java.time.LocalDateTime;


@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @Column(length = 10)
    private String phone;

    private String name;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Integer currentQuestionIndex;

    @Lob
    @Column(columnDefinition = "JSON")
    private String answersJson;

    private Boolean submitted;

    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;
}
