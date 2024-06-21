package com.gerenciar.contas;

import com.gerenciar.contas.conta.api.dto.ContaDTO;
import com.gerenciar.contas.conta.converter.ContaDtoToContaConverter;
import com.gerenciar.contas.conta.domain.model.Conta;
import com.gerenciar.contas.conta.domain.service.ContaService;
import com.gerenciar.contas.conta.service.ContaApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ContaApplicationServiceTest {

    @Mock
    private ContaService contaService;

    @Mock
    private ContaDtoToContaConverter dtoToContaConverter;

    @InjectMocks
    private ContaApplicationService contaApplicationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        ContaDTO contaDTO =  ContaDTO.builder().build();
        Conta conta = new Conta();
        when(dtoToContaConverter.apply(contaDTO)).thenReturn(conta);
        when(contaService.save(conta)).thenReturn(conta);

        Conta result = contaApplicationService.save(contaDTO);

        assertNotNull(result);
        assertEquals(conta, result);
    }

    @Test
    void testFindById() {
        Long id = 1L;
        Conta conta = new Conta();
        when(contaService.findById(id)).thenReturn(Optional.of(conta));
        Optional<Conta> result = contaApplicationService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(conta, result.get());
    }



    @Test
    void testTratamentoArquivo() throws IOException {

        String csvContent = "dataVencimento;dataPagamento;valor;descricao;situacao\n"
                + "23/11/2024;24/11/2024;100.00;Descrição da conta;PENDENTE";
        InputStream inputStream = new ByteArrayInputStream(csvContent.getBytes(StandardCharsets.UTF_8));
        MultipartFile fileCsv = new MockMultipartFile("file.csv", inputStream);

        when(dtoToContaConverter.apply(any())).thenReturn(new Conta());
        contaApplicationService.tratamentoArquivo(fileCsv);

        verify(contaService, times(1)).saveAll(any());
    }
}
