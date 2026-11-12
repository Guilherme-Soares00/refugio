package br.com.fiap.refugio.dto;

import br.com.fiap.refugio.model.CategoriaOcorrencia;
import br.com.fiap.refugio.model.Ocorrencia;
import br.com.fiap.refugio.model.PrioridadeOcorrencia;
import br.com.fiap.refugio.model.StatusOcorrencia;

import org.springframework.hateoas.server.core.Relation;

import java.time.Instant;

@Relation(collectionRelation = "ocorrencias")
public record OcorrenciaDetail(
        Long id,
        Long pontoId,
        Long usuarioId,
        CategoriaOcorrencia categoria,
        String descricao,
        PrioridadeOcorrencia prioridade,
        StatusOcorrencia status,
        Instant abertaEm,
        Instant resolvidaEm) {
    public static OcorrenciaDetail de(Ocorrencia o) {
        return new OcorrenciaDetail(
                o.getId(),
                o.getPonto().getId(),
                o.getUsuario().getId(),
                o.getCategoria(),
                o.getDescricao(),
                o.getPrioridade(),
                o.getStatus(),
                o.getAbertaEm(),
                o.getResolvidaEm());
    }
}
