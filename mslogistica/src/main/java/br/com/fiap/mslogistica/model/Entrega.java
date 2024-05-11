package br.com.fiap.mslogistica.model;

import br.com.fiap.mslogistica.model.enums.EntregaStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long pedidoId;

    @Column(nullable = false)
    private EntregaStatus status;

    @OneToOne
    @JoinColumn(name = "origem_id", referencedColumnName = "id", nullable = false)
    private Endereco origem;

    @OneToOne
    @JoinColumn(name = "destino_id", referencedColumnName = "id", nullable = false)
    private Endereco destino;

    @ManyToOne
    @JoinColumn(name = "entregador_id", referencedColumnName = "id", nullable = false)
    private Entregador entregador;
}
