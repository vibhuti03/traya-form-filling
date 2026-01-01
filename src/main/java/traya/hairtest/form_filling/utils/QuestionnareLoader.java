package traya.hairtest.form_filling.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import traya.hairtest.form_filling.dto.Questionnare;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class QuestionnareLoader {

    private final ObjectMapper objectMapper;
    private final Map<String, Questionnare> cache = new HashMap<>();

    @PostConstruct
    public void load() {
        cache.put("MALE", load("questionnare/male.json"));
        cache.put("FEMALE", load("questionnare/female.json"));
    }

    private Questionnare load(String path) {
        try (InputStream is =
                     getClass().getClassLoader().getResourceAsStream(path)) {
            return objectMapper.readValue(is, Questionnare.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load " + path, e);
        }
    }

    public Questionnare get(String gender) {
        return cache.get(gender.toUpperCase());
    }
}
