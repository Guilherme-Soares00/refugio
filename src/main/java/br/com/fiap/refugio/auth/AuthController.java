package br.com.fiap.refugio.auth;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService service;
    public AuthController(AuthService service) { this.service = service; }
    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioResponse> cadastrar(@Valid @RequestBody CadastroRequest dto) {
        return ResponseEntity.status(201).body(service.cadastrar(dto));
    }
    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody LoginRequest dto) { return service.login(dto); }
}
