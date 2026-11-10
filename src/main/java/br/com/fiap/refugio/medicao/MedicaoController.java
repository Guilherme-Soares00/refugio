package br.com.fiap.refugio.medicao;

import br.com.fiap.refugio.common.Pagina;
import br.com.fiap.refugio.common.Paginacao;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController
@RequestMapping("/api")
public class MedicaoController {
    private final MedicaoService service;

    public MedicaoController(MedicaoService service) {
        this.service = service;
    }

    @PostMapping("/medicoes")
    public ResponseEntity<MedicaoDetail> criar(@Valid @RequestBody MedicaoCreate dto) {
        MedicaoDetail criado = service.criar(dto);
        return ResponseEntity.created(URI.create("/api/pontos/" + criado.pontoId() + "/medicoes")).body(criado);
    }

    @GetMapping("/pontos/{id}/medicoes")
    public Pagina<MedicaoDetail> listar(@PathVariable Long id,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size,
            @RequestParam MultiValueMap<String, String> parametros) {
        return service.listar(id, new Paginacao(page, size, parametros.get("sort")));
    }
}
