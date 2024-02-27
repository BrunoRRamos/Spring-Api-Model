package spring.example.api.controller.endereco;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Classe de Teste Endereco")
public class EnderecoV1RestControllerTest {
    final String URL_TEMPLATE = "/v1/logradouros";
    @Autowired
    MockMvc driver;

    @Autowired
    ObjectMapper objectMapper;
    EnderecoPutPostDTO enderecoPutPostDTO;

    @BeforeEach
    void setup(){
        objectMapper = new ObjectMapper();

        enderecoPutPostDTO = EnderecoPutPostDTO.builder()
                .rua("Rua Floriano Peixoto")
                .bairro("Centro")
                .cidade("Campina Grande")
                .numero(14)
                .build();
    }

    @Test
    @DisplayName("Criando endereço")
    void testePostLogradouro() throws Exception{
        String resultadoSTR = driver.perform(MockMvcRequestBuilders.post(URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(enderecoPutPostDTO)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        Endereco enderecoResultado = objectMapper.readValue(resultadoSTR, Endereco.class);

        assertNotNull(enderecoResultado.getId());
        assertEquals(enderecoPutPostDTO.getNome(), enderecoResultado.getNome());
        assertEquals(enderecoPutPostDTO.getCep(), enderecoResultado.getCep());
    }

    @Test
    @DisplayName("Alterando endereço")
    void testePutLogradouro() throws Exception{
        String resultadoSTR = driver.perform(MockMvcRequestBuilders.post(URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(enderecoPutPostDTO)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        Endereco endereco = objectMapper.readValue(resultadoSTR, Endereco.class);

        EnderecoPutPostDTO attEndereco = EnderecoPutPostDTO.builder()
                .rua("Rua Maria da Penha")
                .bairro("Bessa")
                .cidade("João Pessoa")
                .numero(69)
                .build();

        resultadoSTR = driver.perform(MockMvcRequestBuilders.put(URL_TEMPLATE + "/" + endereco.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(enderecoPutPostDTO)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        Endereco enderecoResultado = objectMapper.readValue(resultadoSTR, Endereco.class);

        //Asserts
        assertNotNull(enderecoResultado.getId());
        assertEquals(enderecoPutPostDTO.getNome(), enderecoResultado.getNome());

    }

    @Test
    @DisplayName("Obtendo endereço por ID")
    void testeGetOneLogradouro() throws Exception {
        String criando = driver.perform(MockMvcRequestBuilders.post(URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(enderecoPutPostDTO)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        String logradouroResultado = driver.perform(get(URL_TEMPLATE + "/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(enderecoPutPostDTO)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        Endereco endereco = objectMapper.readValue(logradouroResultado, Endereco.class);

        assertEquals(endereco.getId(),1);

        ResultActions enderecoInexistente = driver.perform(get(URL_TEMPLATE + "/5")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(enderecoPutPostDTO)))
                .andExpect(status().is(404));// Esperado que não encontre o endereço.

    }

    @Test
    @DisplayName("Obtendo Todos os Enderecos")
    void testePatchLogradouro() throws Exception{
        //Arrange
        String endereco1 = driver.perform(MockMvcRequestBuilders.post(URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(enderecoPutPostDTO)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        EnderecoPutPostDTO attEndereco = EnderecoPutPostDTO.builder()
                .rua("Rua Maria da Penha")
                .bairro("Bessa")
                .cidade("João Pessoa")
                .numero(69)
                .build();

        String endereco2 = driver.perform(MockMvcRequestBuilders.post(URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(enderecoPutPostDTO)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        //Act
        String responseJsonString = driver.perform(get(URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(enderecoPutPostDTO)))
                .andExpect(status().isOk()) // Codigo 200
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        List<Endereco> resultado = objectMapper.readValue(responseJsonString, new TypeReference<>() {
        });

        //Asserts
        assertEquals(resultado.size(),2);
    }

    @Test
    @DisplayName("Quando excluímos um endereço salvo")
    void quandoExcluimosClienteValido() throws Exception {
        // Arrange
        String resultadoSTR = driver.perform(MockMvcRequestBuilders.post(URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(enderecoPutPostDTO)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        Endereco endereco = objectMapper.readValue(resultadoSTR, Endereco.class);

        // Act
        String responseJsonString = driver.perform(delete(URL_TEMPLATE + "/" + endereco.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(enderecoPutPostDTO)))
                .andExpect(status().isNoContent()) // Codigo 204
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        // Assert
        assertTrue(responseJsonString.isBlank());
    }
}
