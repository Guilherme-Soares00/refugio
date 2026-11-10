package br.com.fiap.refugio.recurso;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.fiap.refugio.ponto.PontoRefugio;

public interface RecursoPontoRepository extends JpaRepository<RecursoPonto, Long> {
    boolean existsByPontoAndTipo(PontoRefugio ponto, TipoRecurso tipo);
}
