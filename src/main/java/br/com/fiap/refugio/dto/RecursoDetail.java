package br.com.fiap.refugio.dto;

import br.com.fiap.refugio.model.RecursoPonto;
import br.com.fiap.refugio.model.TipoRecurso;


import java.time.Instant;

@org.springframework.hateoas.server.core.Relation(collectionRelation="recursos")
public record RecursoDetail(Long id, Long pontoId, TipoRecurso tipo, boolean disponivel, Instant atualizadoEm) {
    public static RecursoDetail de(RecursoPonto recurso) {
        return new RecursoDetail(recurso.getId(), recurso.getPonto().getId(), recurso.getTipo(),
                recurso.isDisponivel(), recurso.getAtualizadoEm());
    }
}
