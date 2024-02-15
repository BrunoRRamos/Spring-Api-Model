package spring.example.api.service;

import spring.example.api.dto.ClienteDto;
import spring.example.api.model.Cliente;

import java.util.Collection;

public interface ClienteService {
    public Cliente criarCliente(ClienteDto clienteDto);
    public Cliente buscaClienteId(Long id);
    public Collection<Cliente> listaTodosClientes();
    public Cliente modificaCliente(Long id, ClienteDto clienteDados);
    public void removeCliente(Long id);
}
