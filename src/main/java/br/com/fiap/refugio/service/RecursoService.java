package br.com.fiap.refugio.service;

import br.com.fiap.refugio.config.ApiException;
import br.com.fiap.refugio.dto.RecursoCreate;
import br.com.fiap.refugio.dto.RecursoDetail;
import br.com.fiap.refugio.dto.RecursoUpdate;
import br.com.fiap.refugio.model.PontoRefugio;
import br.com.fiap.refugio.model.RecursoPonto;
import br.com.fiap.refugio.repository.PontoRefugioRepository;
import br.com.fiap.refugio.repository.RecursoPontoRepository;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.List;

@Service
public class RecursoService {
    private final RecursoPontoRepository recursos;
    private final PontoRefugioRepository pontos;
    private final org.springframework.cache.CacheManager cache;

    public RecursoService(RecursoPontoRepository recursos, PontoRefugioRepository pontos, org.springframework.cache.CacheManager cache) {
        this.recursos = recursos;
        this.pontos = pontos;
        this.cache = cache;
    }

    @Transactional(readOnly = true)
    public List<RecursoDetail> listar(Long pontoId) {
        ponto(pontoId);
        return recursos.findByPontoIdOrderByIdAsc(pontoId).stream().map(RecursoDetail::de).toList();
    }

    @Transactional
    @org.springframework.cache.annotation.CacheEvict(value="pontoDetalhe", key="#dto.pontoId()")
    public RecursoDetail criar(RecursoCreate dto) {
        return RecursoDetail.de(recursos.saveAndFlush(new RecursoPonto(ponto(dto.pontoId()), dto.tipo(),
                dto.disponivel(), Instant.now())));
    }

    @Transactional
    public void atualizar(Long id, RecursoUpdate dto) {
        RecursoPonto recurso = exigir(id);
        Long anterior = recurso.getPonto().getId();
        recurso.setPonto(ponto(dto.pontoId()));
        recurso.setTipo(dto.tipo());
        recurso.setDisponivel(dto.disponivel());
        recurso.setAtualizadoEm(Instant.now());
        recursos.flush();
        cache.getCache("pontoDetalhe").evict(anterior);
        cache.getCache("pontoDetalhe").evict(dto.pontoId());
    }

    @Transactional
    public void excluir(Long id) {
        var recurso = exigir(id);
        Long pontoId = recurso.getPonto().getId();
        recursos.delete(recurso);
        recursos.flush();
        cache.getCache("pontoDetalhe").evict(pontoId);
    }

    private RecursoPonto exigir(Long id) {
        return recursos.findById(id).orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Recurso não encontrado"));
    }

    private PontoRefugio ponto(Long id) {
        return pontos.findById(id).orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Ponto não encontrado"));
    }
}
