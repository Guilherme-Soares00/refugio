package br.com.fiap.refugio;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.assertj.core.api.Assertions.assertThat;

class RefugioApplicationTest {

    @Test
    void declaraEntradaSpringBootNoPacoteBase() throws Exception {
        Class<?> application = Class.forName("br.com.fiap.refugio.RefugioApplication");

        assertThat(application.isAnnotationPresent(SpringBootApplication.class)).isTrue();
        assertThat(application.getMethod("main", String[].class).getReturnType()).isEqualTo(void.class);
    }
}
