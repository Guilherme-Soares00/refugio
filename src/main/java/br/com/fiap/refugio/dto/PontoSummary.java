package br.com.fiap.refugio.dto;

import br.com.fiap.refugio.model.PontoRefugio;
import br.com.fiap.refugio.model.StatusPonto;


@org.springframework.hateoas.server.core.Relation(collectionRelation="pontos")
public record PontoSummary(Long id, String codigoPublico, String nome, String bairro,
                           String horarioTexto, StatusPonto status) {
    public static PontoSummary de(PontoRefugio ponto) {
        return new PontoSummary(ponto.getId(), ponto.getCodigoPublico(), ponto.getNome(),
                ponto.getBairro(), ponto.getHorarioTexto(), ponto.getStatus());
    }
}
