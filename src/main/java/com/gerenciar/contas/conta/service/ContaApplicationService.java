package com.gerenciar.contas.conta.service;

import com.gerenciar.contas.conta.api.dto.ContaDTO;
import com.gerenciar.contas.conta.converter.ContaDtoToContaConverter;
import com.gerenciar.contas.conta.domain.enums.Situacao;
import com.gerenciar.contas.conta.domain.model.Conta;
import com.gerenciar.contas.conta.domain.service.ContaService;
import com.gerenciar.contas.conta.util.TratarData;
import lombok.SneakyThrows;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ContaApplicationService {

    @Autowired
    private ContaService contaService;

    @Autowired
    private ContaDtoToContaConverter dtoToContaConverter;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public Conta save(ContaDTO contaDTO) {
        return contaService.save(dtoToContaConverter.apply(contaDTO));
    }

    public Optional<Conta> findById(Long id) {

        return contaService.findById(id);
    }

    public Page findAll(Pageable page, String descricao, LocalDate dataVencimento) {
        return contaService.findAll(page, Conta.builder().descricao(descricao)
                .dataVencimento(dataVencimento).build());
    }

    public void deleteById(Long id) {

        contaService.deleteById(id);
    }

    public void updateSituacao(Long id, Situacao situacao) {
        contaService.updateSituacao(id, situacao);
    }

    @SneakyThrows
    public void tratamentoArquivo(MultipartFile fileCsv) throws IOException {
        List<Conta> contasList = new ArrayList<>();


        try (BufferedReader reader = new BufferedReader(new InputStreamReader(fileCsv.getInputStream(), StandardCharsets.UTF_8));
             CSVParser csvParser = CSVFormat.Builder.create(CSVFormat.DEFAULT)
                     .setHeader()
                     .setIgnoreHeaderCase(true)
                     .setTrim(true)
                     .setDelimiter(';')
                     .build()
                     .parse(reader)) {

            for (CSVRecord csvRecord : csvParser) {

                contasList.add( Conta.builder()
                                .dataVencimento(TratarData.converterDataPadraoBd(csvRecord.get("dataVencimento")))
                                .dataPagamento(TratarData.converterDataPadraoBd(csvRecord.get("dataPagamento")))
                                .valor(new BigDecimal( csvRecord.get("valor").replace(",",".")))
                                .descricao(csvRecord.get("descricao"))
                                .situacao(Situacao.valueOf(csvRecord.get("situacao")))
                        .build());
            }

        }
        contaService.saveAll(contasList);
    }


}