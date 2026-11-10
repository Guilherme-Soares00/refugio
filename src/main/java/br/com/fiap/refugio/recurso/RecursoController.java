package br.com.fiap.refugio.recurso;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RecursoController {
    private final RecursoService service;

    public RecursoController(RecursoService service) {
        this.service = service;
    }

    @GetMapping("/pontos/{id}/recursos")
    public List<RecursoDetail> listar(@PathVariable Long id) {
        return service.listar(id);
    }

    @PostMapping("/recursos")
    public ResponseEntity<RecursoDetail> criar(@Valid @RequestBody RecursoCreate dto) {
        RecursoDetail criado = service.criar(dto);
        return ResponseEntity.created(URI.create("/api/pontos/" + criado.pontoId() + "/recursos")).body(criado);
    }

    @PutMapping("/recursos/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @Valid @RequestBody RecursoUpdate dto) {
        service.atualizar(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/recursos/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
