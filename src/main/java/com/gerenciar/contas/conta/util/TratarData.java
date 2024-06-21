package com.gerenciar.contas.conta.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Utilitário para conversão de datas em formatos comuns
 * para o formato padrão de banco de dados (yyyy-MM-dd).
 */
public class TratarData {

    // Padrões de regex para datas no formato dd/MM/yyyy e dd-MM-yyyy
    static final String DATA_DDMMYYYY_BARRA = "\\b(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(19|20)\\d{2}\\b";
    static final String DATA_DDMMYYYY_TRACO = "\\b(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(19|20)\\d{2}\\b";


    /**
     * Converte uma data nos formatos dd/MM/yyyy ou dd-MM-yyyy para um LocalDate.
     *
     * @param data A data em formato de string para ser convertida.
     * @return Um objeto LocalDate representando a data fornecida, ou null se o formato não for reconhecido.
     */
    public static LocalDate converterDataPadraoBd(String data){


       Pattern pattern = Pattern.compile(DATA_DDMMYYYY_BARRA);
       Matcher matcher = pattern.matcher(data);
       if(matcher.find()){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(data, formatter);
       }


        pattern = Pattern.compile(DATA_DDMMYYYY_TRACO);
        matcher = pattern.matcher(data);
        if(matcher.find()){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return LocalDate.parse(data, formatter);
        }

        return null;

    }

}
