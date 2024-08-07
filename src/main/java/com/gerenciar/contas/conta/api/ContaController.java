package com.gerenciar.contas.conta.api;

import com.gerenciar.contas.conta.api.dto.ContaDTO;
import com.gerenciar.contas.conta.api.dto.TotalDTO;
import com.gerenciar.contas.conta.domain.enums.Situacao;
import com.gerenciar.contas.conta.domain.model.Conta;
import com.gerenciar.contas.conta.service.ContaApplicationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/contas")
@SecurityRequirement(name = "bearerAuth")
public class ContaController {

    @Autowired
    private ContaApplicationService contaApplicationService;


    @PostMapping(value= "/create", consumes = "application/json")
    public ResponseEntity<Conta> createConta(@Valid @RequestBody ContaDTO contaDTO) {

        return new ResponseEntity<>(contaApplicationService.save(contaDTO), HttpStatus.CREATED);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Conta> getContaById(@PathVariable Long id) {
        Optional<Conta> conta = contaApplicationService.findById(id);
        return conta.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/find")
    public ResponseEntity<Page> getAllContas(
            @RequestParam(value = "descricao", required = false) String descricao,
            @RequestParam(value = "dataVencimento", required = false) LocalDate dataVencimento,
            @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize) {

        Pageable page = PageRequest.of(pageNumber, pageSize);

        return new ResponseEntity<>(contaApplicationService.findAll(page, descricao, dataVencimento), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Conta> updateConta(@PathVariable Long id, @Valid @RequestBody ContaDTO contaDTO) {
        if (contaApplicationService.findById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(contaApplicationService.save(contaDTO), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteConta(@PathVariable Long id) {
        contaApplicationService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Void> updateSituacao(@PathVariable Long id, @RequestParam Situacao situacao) {
        contaApplicationService.updateSituacao(id, situacao);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/upload/csv")
    public ResponseEntity<Void> importContas(@RequestParam("file") MultipartFile file) throws IOException {

        contaApplicationService.tratamentoArquivo(file);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/totalPago")
    public ResponseEntity<TotalDTO> getContaById(
            @RequestParam(value = "dataInicio", required = false) LocalDate dataInicio,
            @RequestParam(value = "dataFim", required = false) LocalDate dataFim
    ) {
        var total = contaApplicationService.getSumByBetweenDate(dataInicio,dataFim);
        return  new ResponseEntity<>(total, HttpStatus.OK);

    }
}