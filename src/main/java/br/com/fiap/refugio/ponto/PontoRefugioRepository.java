package br.com.fiap.refugio.ponto;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PontoRefugioRepository extends JpaRepository<PontoRefugio, Long> {
    Optional<PontoRefugio> findByCodigoPublico(String codigoPublico);
}
