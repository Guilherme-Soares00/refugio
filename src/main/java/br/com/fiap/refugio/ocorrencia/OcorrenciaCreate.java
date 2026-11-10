package br.com.fiap.refugio.ocorrencia;
import jakarta.validation.constraints.*;
public record OcorrenciaCreate(@NotNull @Positive Long pontoId, @NotNull CategoriaOcorrencia categoria,
                               @NotBlank @Size(min=10,max=500) String descricao) {}
