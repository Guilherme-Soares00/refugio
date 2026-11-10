package br.com.fiap.refugio.auth;
public record TokenResponse(String accessToken, String tipo, long expiresIn) {}
