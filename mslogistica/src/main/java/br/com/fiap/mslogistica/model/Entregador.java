package br.com.fiap.mslogistica.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Entregador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public String nome;

    @Column(nullable = false)
    public Integer idade;

    @OneToOne
    @JoinColumn(name = "endereco_id", referencedColumnName = "id", nullable = false)
    private Endereco endereco;
}
