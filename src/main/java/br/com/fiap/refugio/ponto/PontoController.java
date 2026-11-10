package br.com.fiap.refugio.ponto;

import br.com.fiap.refugio.common.Pagina;
import br.com.fiap.refugio.common.Paginacao;
import jakarta.validation.Valid;
import org.springframework.hateoas.*;
import br.com.fiap.refugio.common.Hipermidia;
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
    public PagedModel<EntityModel<PontoSummary>> listar(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size,
            @RequestParam MultiValueMap<String, String> parametros) {
        return Hipermidia.pagina(service.listar(new Paginacao(page, size, parametros.get("sort"))), this::resumo);
    }

    @GetMapping("/busca")
    public PagedModel<EntityModel<PontoSummary>> buscar(@RequestParam(required = false) String bairro,
            @RequestParam(required = false) StatusPonto status,
            @RequestParam(required = false) br.com.fiap.refugio.recurso.TipoRecurso tipoRecurso,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size,
            @RequestParam MultiValueMap<String, String> parametros) {
        return Hipermidia.pagina(service.buscar(bairro, status, tipoRecurso, new Paginacao(page, size, parametros.get("sort"))), this::resumo);
    }

    @GetMapping("/{id}")
    public EntityModel<PontoDetail> detalhar(@PathVariable Long id, org.springframework.security.core.Authentication authentication) {
        var model = EntityModel.of(service.detalhar(id), Hipermidia.link("/api/pontos/"+id,"self"),
                Hipermidia.link("/api/pontos/"+id+"/recursos","recursos"), Hipermidia.link("/api/pontos/"+id+"/medicoes","medicoes"));
        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a->a.getAuthority().equals("ROLE_GESTOR"))) {
            model.add(Hipermidia.link("/api/pontos/"+id,"atualizar"), Hipermidia.link("/api/pontos/"+id,"excluir"));
        }
        return model;
    }

    private EntityModel<PontoSummary> resumo(PontoSummary dto) {
        return EntityModel.of(dto, Hipermidia.link("/api/pontos/"+dto.id(),"self"));
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
