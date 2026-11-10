package br.com.fiap.refugio.recurso;

import br.com.fiap.refugio.common.ApiException;
import br.com.fiap.refugio.ponto.PontoRefugio;
import br.com.fiap.refugio.ponto.PontoRefugioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.List;

@Service
public class RecursoService {
    private final RecursoPontoRepository recursos;
    private final PontoRefugioRepository pontos;

    public RecursoService(RecursoPontoRepository recursos, PontoRefugioRepository pontos) {
        this.recursos = recursos;
        this.pontos = pontos;
    }

    @Transactional(readOnly = true)
    public List<RecursoDetail> listar(Long pontoId) {
        ponto(pontoId);
        return recursos.findByPontoIdOrderByIdAsc(pontoId).stream().map(RecursoDetail::de).toList();
    }

    @Transactional
    public RecursoDetail criar(RecursoCreate dto) {
        return RecursoDetail.de(recursos.saveAndFlush(new RecursoPonto(ponto(dto.pontoId()), dto.tipo(),
                dto.disponivel(), Instant.now())));
    }

    @Transactional
    public void atualizar(Long id, RecursoUpdate dto) {
        RecursoPonto recurso = exigir(id);
        recurso.setPonto(ponto(dto.pontoId()));
        recurso.setTipo(dto.tipo());
        recurso.setDisponivel(dto.disponivel());
        recurso.setAtualizadoEm(Instant.now());
        recursos.flush();
    }

    @Transactional
    public void excluir(Long id) {
        recursos.delete(exigir(id));
        recursos.flush();
    }

    private RecursoPonto exigir(Long id) {
        return recursos.findById(id).orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Recurso não encontrado"));
    }

    private PontoRefugio ponto(Long id) {
        return pontos.findById(id).orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Ponto não encontrado"));
    }
}
