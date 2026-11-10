package br.com.fiap.refugio.medicao;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.Instant;
import br.com.fiap.refugio.ponto.PontoRefugio;

public interface MedicaoRepository extends JpaRepository<Medicao, Long> {
    boolean existsByPontoAndOrigemAndMedidaEm(PontoRefugio ponto, OrigemMedicao origem, Instant medidaEm);
}
