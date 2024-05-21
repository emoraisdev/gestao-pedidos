package br.com.fiap.mslogistica.model;

import br.com.fiap.mslogistica.model.enums.EntregaStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String pedidoId;

    @Column(nullable = false)
    private EntregaStatus status;


    @Transient
    private Endereco origem = Endereco.builder().rua("XV de Novembro")
            .bairro("Centro").cidade("Curitiba").cep("80060-000").numero("971")
            .estado("PR").pais("Brasil").build();

    @ManyToOne
    @JoinColumn(name = "destino_id", referencedColumnName = "id", nullable = false)
    private Endereco destino;

    @ManyToOne
    @JoinColumn(name = "entregador_id", referencedColumnName = "id", nullable = false)
    private Entregador entregador;

    @Transient
    private String urlRota;

    @Embedded
    private Coordenada localizacaoEntregador;

}
