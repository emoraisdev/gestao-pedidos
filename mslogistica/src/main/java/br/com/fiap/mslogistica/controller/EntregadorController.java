package br.com.fiap.mslogistica.controller;

import br.com.fiap.mslogistica.exception.EntityNotFoundException;
import br.com.fiap.mslogistica.model.Entregador;
import br.com.fiap.mslogistica.service.EntregadorService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/entregadores")
@AllArgsConstructor
public class EntregadorController {

    private final EntregadorService service;

    @PostMapping
    public ResponseEntity<Entregador> incluir(@RequestBody Entregador entregador){

        var entregadorSalvo = service.incluir(entregador);

        return new ResponseEntity<>(entregadorSalvo, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscar(@PathVariable Long id) {

        try {
            var entregador = service.buscar(id);
            return new ResponseEntity<>(entregador, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> alterar(@RequestBody Entregador entregador) {

        try {
            var entregadorAlterado = service.alterar(entregador);
            return new ResponseEntity<>(entregadorAlterado, HttpStatus.ACCEPTED);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> remover(@PathVariable Long id) {

        try {
            service.remover(id);
            return new ResponseEntity<>("Entregador Removido", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Page<Entregador>> listar(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {

        var entregadores = service.listar(PageRequest.of(page, size));

        return new ResponseEntity<>(entregadores, HttpStatus.OK);
    }
}
