package com.gerenciar.contas.api;

import com.gerenciar.contas.api.dto.ContaDTO;
import com.gerenciar.contas.application.service.ContaApplicationService;
import com.gerenciar.contas.domain.enums.Situacao;
import com.gerenciar.contas.domain.model.Conta;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contas")
public class ContaController {

    @Autowired
    private ContaApplicationService contaApplicationService;

    @PostMapping
    public ResponseEntity<Conta> createConta(@Valid @RequestBody ContaDTO contaDTO) {
        Conta conta = new Conta();

        return new ResponseEntity<>(contaApplicationService.save(conta), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conta> getContaById(@PathVariable Long id) {
        Optional<Conta> conta = contaApplicationService.findById(id);
        return conta.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Conta>> getAllContas(Pageable pageable) {
        return new ResponseEntity<>(contaApplicationService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Conta> updateConta(@PathVariable Long id, @Valid @RequestBody ContaDTO contaDTO) {
        if (!contaApplicationService.findById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Conta conta = new Conta();
        conta.setId(id);
        return new ResponseEntity<>(contaApplicationService.save(conta), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConta(@PathVariable Long id) {
        contaApplicationService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}/situacao")
    public ResponseEntity<Void> updateSituacao(@PathVariable Long id, @RequestParam Situacao situacao) {
        contaApplicationService.updateSituacao(id, situacao);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/import")
    public ResponseEntity<Void> importContas(@RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}