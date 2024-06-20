package com.gerenciar.contas.domain.service;

import com.gerenciar.contas.domain.enums.Situacao;
import com.gerenciar.contas.domain.model.Conta;
import com.gerenciar.contas.domain.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    public Conta save(Conta conta) {
        return contaRepository.save(conta);
    }

    public Optional<Conta> findById(Long id) {
        return contaRepository.findById(id);
    }

    public List<Conta> findAll() {
        return contaRepository.findAll();
    }

    public void deleteById(Long id) {
        contaRepository.deleteById(id);
    }
    public void updateSituacao(Long id, Situacao situacao) {
        Conta conta = contaRepository.findById(id).orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        conta.setSituacao(situacao);
        contaRepository.save(conta);
    }
}