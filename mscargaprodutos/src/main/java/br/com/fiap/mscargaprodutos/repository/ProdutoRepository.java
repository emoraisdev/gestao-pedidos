package br.com.fiap.mscargaprodutos.repository;


import br.com.fiap.mscargaprodutos.model.Produto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    @Query("SELECT p FROM Produto p WHERE p.enviado = :enviado AND p.horarioExecucao <= :horario")
    Slice<Produto> findByEnviadoAndHorarioExecucaoBefore(@Param("enviado") String enviado, @Param("horario") LocalDateTime horario, Pageable pageable);
}
