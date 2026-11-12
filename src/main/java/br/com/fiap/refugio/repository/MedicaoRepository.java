package br.com.fiap.refugio.repository;

import br.com.fiap.refugio.model.Medicao;
import br.com.fiap.refugio.model.OrigemMedicao;
import br.com.fiap.refugio.model.PontoRefugio;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import java.time.Instant;

public interface MedicaoRepository extends JpaRepository<Medicao, Long> {
    Page<Medicao> findByPontoId(Long pontoId, Pageable pageable);

    Optional<Medicao> findFirstByPontoIdOrderByMedidaEmDescIdDesc(Long pontoId);

    boolean existsByPontoAndOrigemAndMedidaEm(PontoRefugio ponto, OrigemMedicao origem, Instant medidaEm);
}
