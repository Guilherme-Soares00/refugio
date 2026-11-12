package br.com.fiap.refugio.repository;

import br.com.fiap.refugio.model.CategoriaOcorrencia;
import br.com.fiap.refugio.model.Ocorrencia;
import br.com.fiap.refugio.model.PontoRefugio;
import br.com.fiap.refugio.model.Usuario;


import org.springframework.data.jpa.repository.JpaRepository;
import java.time.Instant;

public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {
    @org.springframework.data.jpa.repository.Lock(jakarta.persistence.LockModeType.PESSIMISTIC_WRITE)
    @org.springframework.data.jpa.repository.Query("select o from Ocorrencia o where o.id = :id")
    java.util.Optional<Ocorrencia> findByIdParaAtualizarStatus(@org.springframework.data.repository.query.Param("id") Long id);
    org.springframework.data.domain.Page<Ocorrencia> findByUsuarioId(Long usuarioId, org.springframework.data.domain.Pageable pageable);
    boolean existsByPontoAndUsuarioAndCategoriaAndAbertaEm(PontoRefugio ponto, Usuario usuario, CategoriaOcorrencia categoria, Instant abertaEm);
}
