package spring.example.api.repository.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.example.api.model.Cliente.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
