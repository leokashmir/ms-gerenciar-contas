package com.gerenciar.contas.application.service;

import com.gerenciar.contas.domain.enums.Situacao;
import com.gerenciar.contas.domain.model.Conta;
import com.gerenciar.contas.domain.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContaApplicationService {

    @Autowired
    private ContaService contaService;

    public Conta save(Conta conta) {
        return contaService.save(conta);
    }

    public Optional<Conta> findById(Long id) {
        return contaService.findById(id);
    }

    public List<Conta> findAll() {
        return contaService.findAll();
    }

    public void deleteById(Long id) {
        contaService.deleteById(id);
    }

    public void updateSituacao(Long id, Situacao situacao) {
        contaService.updateSituacao(id, situacao);
    }
}