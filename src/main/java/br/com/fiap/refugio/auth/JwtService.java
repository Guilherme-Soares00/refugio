package br.com.fiap.refugio.auth;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
@Service
public class JwtService {
    private final SecretKey key;
    private final long expiracao;
    public JwtService(@Value("${app.jwt.secret}") String secret,
                      @Value("${app.jwt.expiration-seconds}") long expiracao) {
        try {
            byte[] bytes = Base64.getDecoder().decode(secret);
            if (bytes.length < 32 || expiracao < 1) throw new IllegalArgumentException();
            this.key = Keys.hmacShaKeyFor(bytes);
            this.expiracao = expiracao;
        } catch (IllegalArgumentException ex) {
            throw new IllegalStateException("JWT_SECRET deve ser Base64 de ao menos 256 bits e a expiração deve ser positiva");
        }
    }
    public String emitir(Long id) {
        Instant agora = Instant.now();
        return Jwts.builder().subject(id.toString()).issuedAt(Date.from(agora))
                .expiration(Date.from(agora.plusSeconds(expiracao))).signWith(key).compact();
    }
    public Long usuarioId(String token) {
        return Long.valueOf(Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject());
    }
    public long expiracao() { return expiracao; }
}
