package br.com.fiap.refugio.ocorrencia;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.Instant;
import br.com.fiap.refugio.ponto.PontoRefugio;
import br.com.fiap.refugio.auth.Usuario;

public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {
    boolean existsByPontoAndUsuarioAndCategoriaAndAbertaEm(PontoRefugio ponto, Usuario usuario, CategoriaOcorrencia categoria, Instant abertaEm);
}
