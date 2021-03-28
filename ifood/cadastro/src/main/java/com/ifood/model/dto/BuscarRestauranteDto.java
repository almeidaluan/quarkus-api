package com.ifood.model.dto;

import com.ifood.model.Localizacao;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class BuscarRestauranteDto {

    private String proprietario;

    private String cnpjl;

    private Localizacao localizacao;

    private String nome;


    public LocalDate dataCriacao;

    public LocalDate dataAtualizacao;

    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    public String getCnpjl() {
        return cnpjl;
    }

    public void setCnpjl(String cnpjl) {
        this.cnpjl = cnpjl;
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String text = dataCriacao.format(formatters);
        LocalDate parsedDate = LocalDate.parse(text, formatters);
        this.dataCriacao = parsedDate;
    }

    public LocalDate getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDate dataAtualizacao) {
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String text = dataAtualizacao.format(formatters);
        LocalDate parsedDate = LocalDate.parse(text, formatters);

        this.dataAtualizacao = parsedDate;
    }
}
