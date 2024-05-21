package br.com.fiap.msclientes.Endereco;

import br.com.fiap.msclientes.model.Endereco;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class EnderecoTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpClass() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void whenAllFieldsAreCorrect_thenNoViolations() {
        Endereco endereco = new Endereco("Rua ABC", "123", "Bairro XYZ", "Cidade QRS", "Estado DEF", "País GHI", "12345-678");
        Set<ConstraintViolation<Endereco>> violations = validator.validate(endereco);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void whenStreetIsBlank_thenOneViolation() {
        Endereco endereco = new Endereco("", "123", "Bairro XYZ", "Cidade QRS", "Estado DEF", "País GHI", "12345-678");
        Set<ConstraintViolation<Endereco>> violations = validator.validate(endereco);
        assertEquals(1, violations.size());
        assertEquals("A rua é obrigatória", violations.iterator().next().getMessage());
    }

    @Test
    public void whenNumberIsBlank_thenOneViolation() {
        Endereco endereco = new Endereco("Rua ABC", "", "Bairro XYZ", "Cidade QRS", "Estado DEF", "País GHI", "12345-678");
        Set<ConstraintViolation<Endereco>> violations = validator.validate(endereco);
        assertEquals(1, violations.size());
        assertEquals("O número é obrigatório", violations.iterator().next().getMessage());
    }

    @Test
    public void whenBairroIsBlank_thenOneViolation() {
        Endereco endereco = new Endereco("Rua ABC", "123", "", "Cidade QRS", "Estado DEF", "País GHI", "12345-678");
        Set<ConstraintViolation<Endereco>> violations = validator.validate(endereco);
        assertEquals(1, violations.size());
        assertEquals("O bairro é obrigatório", violations.iterator().next().getMessage());
    }

    @Test
    public void whenCityIsBlank_thenOneViolation() {
        Endereco endereco = new Endereco("Rua ABC", "123", "Bairro XYZ", "", "Estado DEF", "País GHI", "12345-678");
        Set<ConstraintViolation<Endereco>> violations = validator.validate(endereco);
        assertEquals(1, violations.size());
        assertEquals("A cidade é obrigatória", violations.iterator().next().getMessage());
    }

    @Test
    public void whenStateIsBlank_thenOneViolation() {
        Endereco endereco = new Endereco("Rua ABC", "123", "Bairro XYZ", "Cidade QRS", "", "País GHI", "12345-678");
        Set<ConstraintViolation<Endereco>> violations = validator.validate(endereco);
        assertEquals(1, violations.size());
        assertEquals("O estado é obrigatório", violations.iterator().next().getMessage());
    }

    @Test
    public void whenCountryIsBlank_thenOneViolation() {
        Endereco endereco = new Endereco("Rua ABC", "123", "Bairro XYZ", "Cidade QRS", "Estado DEF", "", "12345-678");
        Set<ConstraintViolation<Endereco>> violations = validator.validate(endereco);
        assertEquals(1, violations.size());
        assertEquals("O país é obrigatório", violations.iterator().next().getMessage());
    }

    @Test
    public void whenCEPIsBlank_thenOneViolation() {
        Endereco endereco = new Endereco("Rua ABC", "123", "Bairro XYZ", "Cidade QRS", "Estado DEF", "País GHI", "");
        Set<ConstraintViolation<Endereco>> violations = validator.validate(endereco);
        assertEquals(1, violations.size());
        assertEquals("O CEP é obrigatório", violations.iterator().next().getMessage());
    }

    @Test
    public void testNoArgsConstructor() {
        Endereco endereco = new Endereco();
        assertNull(endereco.getRua());  // Assumindo que o valor inicial é nulo
        assertNull(endereco.getNumero());
        assertNull(endereco.getBairro());
        assertNull(endereco.getCidade());
        assertNull(endereco.getEstado());
        assertNull(endereco.getPais());
        assertNull(endereco.getCep());
    }

    @Test
    public void testAllArgsConstructor() {
        Endereco endereco = new Endereco("Rua ABC", "123", "Bairro XYZ", "Cidade QRS", "Estado DEF", "País GHI", "12345-678");
        assertEquals("Rua ABC", endereco.getRua());
        assertEquals("123", endereco.getNumero());
        assertEquals("Bairro XYZ", endereco.getBairro());
        assertEquals("Cidade QRS", endereco.getCidade());
        assertEquals("Estado DEF", endereco.getEstado());
        assertEquals("País GHI", endereco.getPais());
        assertEquals("12345-678", endereco.getCep());
    }

    @Test
    public void testBuilderPattern() {
        Endereco endereco = Endereco.builder()
                .rua("Rua ABC")
                .numero("123")
                .bairro("Bairro XYZ")
                .cidade("Cidade QRS")
                .estado("Estado DEF")
                .pais("País GHI")
                .cep("12345-678")
                .build();

        assertNotNull(endereco);
        assertEquals("Rua ABC", endereco.getRua());
        assertEquals("123", endereco.getNumero());
        assertEquals("Bairro XYZ", endereco.getBairro());
        assertEquals("Cidade QRS", endereco.getCidade());
        assertEquals("Estado DEF", endereco.getEstado());
        assertEquals("País GHI", endereco.getPais());
        assertEquals("12345-678", endereco.getCep());
    }
}
