package com.gerenciar.contas.conta.domain.repository;

import com.gerenciar.contas.conta.domain.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
}