package br.com.fiap.refugio.ponto;

@org.springframework.hateoas.server.core.Relation(collectionRelation="pontos")
public record PontoSummary(Long id, String codigoPublico, String nome, String bairro,
                           String horarioTexto, StatusPonto status) {
    public static PontoSummary de(PontoRefugio ponto) {
        return new PontoSummary(ponto.getId(), ponto.getCodigoPublico(), ponto.getNome(),
                ponto.getBairro(), ponto.getHorarioTexto(), ponto.getStatus());
    }
}
