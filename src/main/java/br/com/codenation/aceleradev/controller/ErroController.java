package br.com.codenation.aceleradev.controller;

import br.com.codenation.aceleradev.comum.AmbienteEnum;
import br.com.codenation.aceleradev.comum.LevelEnum;
import br.com.codenation.aceleradev.domain.Erro;
import br.com.codenation.aceleradev.service.ErroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/erro")
public class ErroController {

    private static ErroService erroService;

    @Autowired
    public ErroController(ErroService erroService) {
        this.erroService = erroService;
    }

    @GetMapping
    public ResponseEntity<Page<Erro>> findAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC, page = 0, size = 24) Pageable pageable) {
        return ResponseEntity.ok(erroService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Erro> findById(@PathVariable Long id) {
        return ResponseEntity.ok(erroService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Erro> save(@RequestBody Erro erro) {
        return ResponseEntity.status(HttpStatus.CREATED).body(erroService.save(erro));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Erro erro) {
        erroService.update(id, erro);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        erroService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ambiente/{ambiente}")
    public ResponseEntity<Page<Erro>> findByTituloOrByLevelOrByUsuarioIdOrByAmbiente(@PageableDefault(sort = "titulo", direction = Sort.Direction.ASC, page = 0, size = 24) Pageable pageable,
                                                              @PathVariable AmbienteEnum ambiente,
                                                              @RequestParam(required = false) String titulo,
                                                              @RequestParam(required = false) LevelEnum level,
                                                              @RequestParam(required = false) Long usuarioId) {
        if (Objects.nonNull(titulo))
            return ResponseEntity.ok(erroService.findByAmbienteAndTitulo(pageable, ambiente, titulo));
        if(Objects.nonNull(level))
            return ResponseEntity.ok(erroService.findByAmbienteAndLevel(pageable, ambiente, level));
        if (Objects.nonNull(usuarioId))
            return ResponseEntity.ok(erroService.findByAmbienteAndUsuarioId(pageable, ambiente, usuarioId));

        return ResponseEntity.ok(erroService.findByAmbiente(pageable, ambiente));
    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<Page<Erro>> findByTitulo(@PageableDefault(sort = "titulo", direction = Sort.Direction.ASC, page = 0, size = 24) Pageable pageable,
                                                   @PathVariable String titulo) {

        return ResponseEntity.ok(erroService.findByTitulo(pageable, titulo));
    }

    @GetMapping("/level/{level}")
    public ResponseEntity<Page<Erro>> findByLevel(@PageableDefault(sort = "titulo", direction = Sort.Direction.ASC, page = 0, size = 24) Pageable pageable,
                                                  @PathVariable LevelEnum level) {
        return ResponseEntity.ok(erroService.findByLevel(pageable, level));
    }

    @GetMapping("/usuarioId/{usuarioId}")
    public ResponseEntity<Page<Erro>> findByUsuarioId(@PageableDefault(sort = "titulo", direction = Sort.Direction.ASC, page = 0, size = 24) Pageable pageable,
                                                      @PathVariable Long usuarioId) {
        return ResponseEntity.ok(erroService.findByUsuarioId(pageable, usuarioId));
    }

}
