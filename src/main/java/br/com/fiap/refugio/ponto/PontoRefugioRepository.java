package br.com.fiap.refugio.ponto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface PontoRefugioRepository extends JpaRepository<PontoRefugio, Long> {
    Optional<PontoRefugio> findByCodigoPublico(String codigoPublico);

    Page<PontoRefugio> findByStatus(StatusPonto status, Pageable pageable);

    @EntityGraph(attributePaths = "recursos")
    Optional<PontoRefugio> findDetalhadoById(Long id);
}
