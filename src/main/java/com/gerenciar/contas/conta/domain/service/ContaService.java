package com.gerenciar.contas.conta.domain.service;

import com.gerenciar.contas.conta.domain.enums.Situacao;
import com.gerenciar.contas.conta.domain.model.Conta;
import com.gerenciar.contas.conta.domain.repository.ContaRepository;
import com.gerenciar.contas.conta.domain.repository.query.specifications.ContaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    private ContaSpecification contaSpecification;

    public Conta save(Conta conta) {
        return contaRepository.save(conta);
    }

    public Optional<Conta> findById(Long id) {
        return contaRepository.findById(id);
    }

    public Page<Conta> findAll(Pageable page, Conta conta) {
        contaSpecification = new ContaSpecification(conta);
        return contaRepository.findAll(contaSpecification.dinamicQuery(), page);
    }

    public void deleteById(Long id) {
        contaRepository.deleteById(id);
    }
    public void updateSituacao(Long id, Situacao situacao) {
        Conta conta = contaRepository.findById(id).orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        conta.setSituacao(situacao);
        contaRepository.save(conta);
    }
    public void saveAll(List<Conta> contasList) {
        contaRepository.saveAll(contasList);
    }

    public Double getSumByBetweenDate(LocalDate dataInicio, LocalDate dataFim){
        return contaRepository.getSumByBetweenDate(dataInicio, dataFim);
    }
}