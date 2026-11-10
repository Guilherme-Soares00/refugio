package br.com.fiap.refugio.recurso;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RecursoCreate(@NotNull @Positive Long pontoId, @NotNull TipoRecurso tipo, @NotNull Boolean disponivel) {
}
