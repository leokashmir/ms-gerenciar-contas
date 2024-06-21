package com.gerenciar.contas.conta.converter;

import com.gerenciar.contas.conta.api.dto.ContaDTO;
import com.gerenciar.contas.conta.domain.model.Conta;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ContaDtoToContaConverter implements Function<ContaDTO, Conta> {
    @Override
    public Conta apply(ContaDTO contaDTO) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(contaDTO, Conta.class);
    }
}
