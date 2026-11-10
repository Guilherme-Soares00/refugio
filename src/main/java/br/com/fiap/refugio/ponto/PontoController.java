package br.com.fiap.refugio.ponto;

import br.com.fiap.refugio.common.Pagina;
import br.com.fiap.refugio.common.Paginacao;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController
@RequestMapping("/api/pontos")
public class PontoController {
    private final PontoService service;

    public PontoController(PontoService service) {
        this.service = service;
    }

    @GetMapping
    public Pagina<PontoSummary> listar(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size,
            @RequestParam MultiValueMap<String, String> parametros) {
        return service.listar(new Paginacao(page, size, parametros.get("sort")));
    }

    @GetMapping("/{id}")
    public PontoDetail detalhar(@PathVariable Long id) {
        return service.detalhar(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @Valid @RequestBody PontoUpdate dto) {
        service.atualizar(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<PontoSummary> criar(@Valid @RequestBody PontoCreate dto) {
        PontoSummary criado = service.criar(dto);
        return ResponseEntity.created(URI.create("/api/pontos/" + criado.id())).body(criado);
    }
}
