package br.com.fiap.mslogistica.model.dto;

import br.com.fiap.mslogistica.model.enums.EntregaStatus;
import jakarta.validation.constraints.Pattern;

public record EntregaStatusDTO(

        @Pattern(regexp = "^[0-2]$", message = "O valor deve ser 0, 1 ou 2")
        EntregaStatus status
) {
}
