
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Info;
import org.junit.jupiter.api.Test;


import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class JacksonTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void jsonCatTest() throws Exception {
        File file = new File("src/test/resources/cat.json");
        Info info = objectMapper.readValue(file, Info.class);
        assertThat(info.getName()).isEqualTo("Matroskin");
        assertThat(info.getOwner()).isEqualTo("Fedor");
        assertThat(info.getAge()).isEqualTo(3);
        assertThat(info.getPassportId()).isEqualTo("ABS123");
        assertThat(info.isPassportValid()).isTrue();
        assertThat(info.getDetails()).isEqualTo(List.of("4 paws", "1 tail", "whiskers"));
    }
}