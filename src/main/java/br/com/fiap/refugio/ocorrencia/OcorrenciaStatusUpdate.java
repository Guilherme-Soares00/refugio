package br.com.fiap.refugio.ocorrencia;
import jakarta.validation.constraints.NotNull;
public record OcorrenciaStatusUpdate(@NotNull StatusOcorrencia status) {}
