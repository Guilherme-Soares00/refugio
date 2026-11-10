package br.com.fiap.refugio.ponto;

import br.com.fiap.refugio.medicao.MedicaoDetail;
import br.com.fiap.refugio.recurso.RecursoDetail;
import java.time.Instant;
import java.util.List;

public record PontoDetail(Long id, String codigoPublico, String nome, String logradouro, String numero,
                          String bairro, String cidade, String uf, String horarioTexto, StatusPonto status,
                          Instant cadastradoEm, List<RecursoDetail> recursos, MedicaoDetail ultimaMedicao,
                          String etiqueta, String textoSemMedicao, String avisoDados) {
}
