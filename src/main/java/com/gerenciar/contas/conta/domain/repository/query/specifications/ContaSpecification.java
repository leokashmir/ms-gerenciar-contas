package com.gerenciar.contas.conta.domain.repository.query.specifications;

import com.gerenciar.contas.conta.domain.model.Conta;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
@Log4j2
@AllArgsConstructor
public class ContaSpecification {

    private Conta conta;

    public Specification<Conta> dinamicQuery(){
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList();
            StringBuilder sb = new StringBuilder();

            if(conta.getDescricao() != null && !conta.getDescricao().isEmpty() ){
                Path<String> campoDescricao = root.get("descricao");
                Predicate predicateDescricao = builder.like(campoDescricao, "%{"+ conta.getDescricao() +"}%");
                predicates.add(predicateDescricao);
            }
            if(conta.getDataVencimento() != null){
                Path<String> campoDataVencimento = root.get("dataVencimento");
                Predicate predicateDataVencimento = builder.equal(campoDataVencimento, conta.getDataVencimento());
                predicates.add(predicateDataVencimento);
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
