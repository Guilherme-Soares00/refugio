package br.com.fiap.refugio.ocorrencia;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.Instant;
import br.com.fiap.refugio.ponto.PontoRefugio;
import br.com.fiap.refugio.auth.Usuario;

public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {
    @org.springframework.data.jpa.repository.Lock(jakarta.persistence.LockModeType.PESSIMISTIC_WRITE)
    @org.springframework.data.jpa.repository.Query("select o from Ocorrencia o where o.id = :id")
    java.util.Optional<Ocorrencia> findByIdParaAtualizarStatus(@org.springframework.data.repository.query.Param("id") Long id);
    org.springframework.data.domain.Page<Ocorrencia> findByUsuarioId(Long usuarioId, org.springframework.data.domain.Pageable pageable);
    boolean existsByPontoAndUsuarioAndCategoriaAndAbertaEm(PontoRefugio ponto, Usuario usuario, CategoriaOcorrencia categoria, Instant abertaEm);
}
