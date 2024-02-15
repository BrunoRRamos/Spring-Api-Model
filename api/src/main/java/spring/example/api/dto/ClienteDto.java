package spring.example.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDto {

    @JsonProperty("nome")
    @NotBlank(message = "Informar o nome é obrigatório")
    private String nome;

    @JsonProperty("cpf")
    @NotBlank(message = "Informar o cpf é obrigatório")
    private String cpf;

    @JsonProperty("idade")
    @NotBlank(message = "Informar a idade é obrigatória")
    private Integer idade;

    @JsonProperty("sexo")
    @NotBlank(message = "Informar o sexo é obrigatório")
    private String sexo;

    @JsonProperty("email")
    @Email
    @NotBlank(message = "Informar o e-mail é obrigatório")
    private String email;
}
