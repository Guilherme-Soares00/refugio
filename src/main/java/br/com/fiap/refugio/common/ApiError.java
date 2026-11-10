package br.com.fiap.refugio.common;

import java.time.Instant;

public record ApiError(int status, String erro, String mensagem, String path, Instant timestamp) {
}
