package br.com.fiap.refugio;

import java.io.DataInputStream;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.assertj.core.api.Assertions.assertThat;

class RefugioApplicationTest {

    @Test
    void geraBytecodeEstavelDoJava25() throws Exception {
        try (var entrada = new DataInputStream(RefugioApplication.class.getResourceAsStream("RefugioApplication.class"))) {
            assertThat(entrada.readInt()).isEqualTo(0xCAFEBABE);
            assertThat(entrada.readUnsignedShort()).isZero();
            assertThat(entrada.readUnsignedShort()).isEqualTo(69);
        }
    }

    @Test
    void declaraEntradaSpringBootNoPacoteBase() throws Exception {
        Class<?> application = Class.forName("br.com.fiap.refugio.RefugioApplication");

        assertThat(application.isAnnotationPresent(SpringBootApplication.class)).isTrue();
        assertThat(application.getMethod("main", String[].class).getReturnType()).isEqualTo(void.class);
    }
}
