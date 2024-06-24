package com.gerenciar.contas.conta.domain.repository;

import com.gerenciar.contas.conta.domain.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ContaRepository extends JpaSpecificationExecutor<Conta>, JpaRepository<Conta, Long> {

    @Query("select sum(c.valor)  from Conta c where c.dataPagamento >= :dataInicio and c.dataPagamento <= :dataFim")
    Double getSumByBetweenDate (@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
}