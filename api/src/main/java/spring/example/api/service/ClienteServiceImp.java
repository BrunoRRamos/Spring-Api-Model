package spring.example.api.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.example.api.dto.ClienteDto;
import spring.example.api.model.Cliente;
import spring.example.api.repository.ClienteRepository;
import java.util.Collection;

@Service
public class ClienteServiceImp implements ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Cliente criarCliente(ClienteDto clienteDto) {
        return this.clienteRepository.save(modelMapper.map(clienteDto, Cliente.class));
    }

    @Override
    public Cliente buscaClienteId(Long id) {
        return this.clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Id inválido, Cliente não encontrado !"));
    }

    @Override
    public Collection<Cliente> listaTodosClientes() {
        return this.clienteRepository.findAll();
    }

    @Override
    public Cliente modificaCliente(Long id, ClienteDto clienteDados) {
        Cliente novoCliente = modelMapper.map(clienteDados, Cliente.class);
        novoCliente.setId(id);

        return modelMapper.map(clienteRepository.saveAndFlush(novoCliente), Cliente.class);
    }

    @Override
    public void removeCliente(Long id) {
        Cliente clienteRemove = this.buscaClienteId(id);
        this.clienteRepository.delete(clienteRemove);
    }
}
