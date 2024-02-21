package spring.example.api.model.Cliente;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "cliente")
public class Cliente {

    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @JsonProperty("nome")
    @Column(name = "nome", nullable = false)
    private String nome;

    @JsonProperty("cpf")
    @Column(name = "cpf", nullable = false)
    private String cpf;

    @JsonProperty("idade")
    @Column(name = "idade", nullable = false)
    private Integer idade;

    @JsonProperty("sexo")
    @Column(name = "sexo", nullable = false)
    private String sexo;

    @JsonProperty("email")
    @Column(name = "email", nullable = false)
    private String email;
}
