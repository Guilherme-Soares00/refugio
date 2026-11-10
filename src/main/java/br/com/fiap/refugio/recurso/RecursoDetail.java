package br.com.fiap.refugio.recurso;

import java.time.Instant;

public record RecursoDetail(Long id, Long pontoId, TipoRecurso tipo, boolean disponivel, Instant atualizadoEm) {
    public static RecursoDetail de(RecursoPonto recurso) {
        return new RecursoDetail(recurso.getId(), recurso.getPonto().getId(), recurso.getTipo(),
                recurso.isDisponivel(), recurso.getAtualizadoEm());
    }
}
