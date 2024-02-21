package spring.example.api.controller.Cliente;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.example.api.dto.Cliente.ClienteDto;
import spring.example.api.model.Cliente.Cliente;
import spring.example.api.service.Cliente.ClienteService;

import java.util.Collection;

@RestController
@RequestMapping(value = "/clientes", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClienteController {

    @Autowired
    public ClienteService clienteServiceImp;

    @Autowired
    public ModelMapper modelMapper;

    @PostMapping
    @Transactional
    public ResponseEntity<Cliente> post(@RequestBody @Valid ClienteDto clienteDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.clienteServiceImp.criarCliente(clienteDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getById(@PathVariable Long id) {
        return ResponseEntity.ok(this.clienteServiceImp.buscaClienteId(id));
    }

    @GetMapping
    public ResponseEntity<Collection<Cliente>> getAll() {
        return ResponseEntity.ok(this.clienteServiceImp.listaTodosClientes());
    }

    @PutMapping(value = "/{id}")
    @Transactional
    public ResponseEntity<Cliente> put(@PathVariable Long id, @RequestBody ClienteDto clienteDto) {
        return ResponseEntity.ok(this.clienteServiceImp.modificaCliente(id, clienteDto));
    }

    @DeleteMapping(value = "/{id}")
    @Transactional
    public ResponseEntity<String> delete(@PathVariable Long id) {
        this.clienteServiceImp.removeCliente(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cliente removido !");
    }
}
