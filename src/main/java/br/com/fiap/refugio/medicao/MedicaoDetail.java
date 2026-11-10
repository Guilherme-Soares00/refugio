package br.com.fiap.refugio.medicao;

import java.math.BigDecimal;
import java.time.Instant;

public record MedicaoDetail(Long id, Long pontoId, BigDecimal temperaturaC, BigDecimal umidadePct,
                            OrigemMedicao origem, Instant medidaEm) {
    public static MedicaoDetail de(Medicao medicao) {
        return new MedicaoDetail(medicao.getId(), medicao.getPonto().getId(), medicao.getTemperaturaC(),
                medicao.getUmidadePct(), medicao.getOrigem(), medicao.getMedidaEm());
    }
}
