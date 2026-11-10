package br.com.fiap.refugio.ponto;

import br.com.fiap.refugio.common.ApiException;
import br.com.fiap.refugio.common.Pagina;
import br.com.fiap.refugio.common.Paginacao;
import br.com.fiap.refugio.medicao.MedicaoDetail;
import br.com.fiap.refugio.medicao.MedicaoRepository;
import br.com.fiap.refugio.recurso.RecursoDetail;
import br.com.fiap.refugio.recurso.RecursoPonto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Comparator;
import java.util.Set;

@Service
public class PontoService {
    private final PontoRefugioRepository pontos;
    private final MedicaoRepository medicoes;
    private final String avisoDados;

    public PontoService(PontoRefugioRepository pontos, MedicaoRepository medicoes,
                        @Value("${app.aviso-demonstracao}") String avisoDados) {
        this.pontos = pontos;
        this.medicoes = medicoes;
        this.avisoDados = avisoDados;
    }

    @Transactional(readOnly = true)
    public Pagina<PontoSummary> listar(Paginacao consulta) {
        var pageable = consulta.pageable(Set.of("id", "codigoPublico", "nome", "bairro", "horarioTexto", "status"),
                Sort.by("nome"));
        return Pagina.de(pontos.findByStatus(StatusPonto.ATIVO, pageable).map(PontoSummary::de));
    }

    @Transactional(readOnly = true)
    public PontoDetail detalhar(Long id) {
        var ponto = pontos.findDetalhadoById(id).orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Ponto não encontrado"));
        var ultima = medicoes.findFirstByPontoIdOrderByMedidaEmDescIdDesc(id)
                .map(MedicaoDetail::de).orElse(null);
        String etiqueta = ultima != null && ultima.temperaturaC().compareTo(new BigDecimal("32")) >= 0
                ? "ATENCAO_DIDATICA" : "INFORMATIVO";
        var recursos = ponto.getRecursos().stream().sorted(Comparator.comparing(RecursoPonto::getId))
                .map(RecursoDetail::de).toList();
        return new PontoDetail(ponto.getId(), ponto.getCodigoPublico(), ponto.getNome(), ponto.getLogradouro(),
                ponto.getNumero(), ponto.getBairro(), ponto.getCidade(), ponto.getUf(), ponto.getHorarioTexto(),
                ponto.getStatus(), ponto.getCadastradoEm(), recursos, ultima, etiqueta,
                ultima == null ? "Sem medição" : null, avisoDados);
    }

    @Transactional
    public void atualizar(Long id, PontoUpdate dto) {
        var ponto = exigir(id);
        ponto.setNome(dto.nome());
        ponto.setLogradouro(dto.logradouro());
        ponto.setNumero(dto.numero());
        ponto.setBairro(dto.bairro());
        ponto.setCidade(dto.cidade());
        ponto.setUf(dto.uf());
        ponto.setHorarioTexto(dto.horarioTexto());
        ponto.setStatus(dto.status());
        pontos.flush();
    }

    @Transactional
    public void excluir(Long id) {
        pontos.delete(exigir(id));
        pontos.flush();
    }

    private PontoRefugio exigir(Long id) {
        return pontos.findById(id).orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Ponto não encontrado"));
    }

    @Transactional
    public PontoSummary criar(PontoCreate dto) {
        return PontoSummary.de(pontos.saveAndFlush(new PontoRefugio(dto.codigoPublico(), dto.nome(),
                dto.logradouro(), dto.numero(), dto.bairro(), dto.cidade(), dto.uf(), dto.horarioTexto(),
                dto.status(), Instant.now())));
    }
}
