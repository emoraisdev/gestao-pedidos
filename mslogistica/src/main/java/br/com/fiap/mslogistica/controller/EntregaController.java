package br.com.fiap.mslogistica.controller;

import br.com.fiap.mslogistica.exception.EntityNotFoundException;
import br.com.fiap.mslogistica.model.Coordenada;
import br.com.fiap.mslogistica.model.Entrega;
import br.com.fiap.mslogistica.service.EntregaService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/entregas")
@AllArgsConstructor
public class EntregaController {

    private final EntregaService service;

    @PostMapping
    public ResponseEntity<Entrega> incluir(@RequestBody Entrega entrega){

        var entregaSalva = service.incluir(entrega);

        return new ResponseEntity<>( entregaSalva, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscar(@PathVariable Long id) {

        try {
            var entrega = service.buscar(id);
            return new ResponseEntity<>(entrega, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> alterar(@RequestBody Entrega entrega) {

        try {
            var entregaAlterada = service.alterar(entrega);
            return new ResponseEntity<>(entregaAlterada, HttpStatus.ACCEPTED);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping(path = "/localEntregador/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> definirLocalEntregador(@PathVariable Long id,
                                                         @RequestBody Coordenada coordenada) {

        try {
            var localDefinido = service.definirLocalEntregador(id, coordenada);
            return new ResponseEntity<>(localDefinido, HttpStatus.ACCEPTED);
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
            return new ResponseEntity<>("Entrega Removida", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Page<Entrega>> listar(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {

        var entregas = service.listar(PageRequest.of(page, size));

        return new ResponseEntity<>(entregas, HttpStatus.OK);
    }
}
