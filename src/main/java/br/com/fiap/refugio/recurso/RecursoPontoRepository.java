package br.com.fiap.refugio.recurso;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import br.com.fiap.refugio.ponto.PontoRefugio;

public interface RecursoPontoRepository extends JpaRepository<RecursoPonto, Long> {
    List<RecursoPonto> findByPontoIdOrderByIdAsc(Long pontoId);

    boolean existsByPontoAndTipo(PontoRefugio ponto, TipoRecurso tipo);
}
