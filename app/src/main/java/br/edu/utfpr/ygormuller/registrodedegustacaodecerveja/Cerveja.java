package br.edu.utfpr.ygormuller.registrodedegustacaodecerveja;

import java.io.Serializable;

public class Cerveja implements Serializable {
    private String nome;
    private String estilo;
    private int ibu;
    private int abv;
    private  String consideracoes;
    private  String nacionalidade;
    private String classificacao;
    private boolean recomendacao;

    public Cerveja(String nome, String estilo, int ibu, int abv, boolean recomendacao,
                   String nacionalidade, String consideracoes, String classificacao) {
        this.nome = nome;
        this.estilo = estilo;
        this.ibu = ibu;
        this.abv = abv;
        this.recomendacao = recomendacao;
        this.nacionalidade = nacionalidade;
        this.consideracoes = consideracoes;
        this.classificacao = classificacao;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public int getIbu() {
        return ibu;
    }

    public void setIbu(int ibu) {
        this.ibu = ibu;
    }

    public int getAbv() {
        return abv;
    }

    public void setAbv(int abv) {
        this.abv = abv;
    }

    public String getConsideracoes() {
        return consideracoes;
    }

    public void setConsideracoes(String consideracoes) {
        this.consideracoes = consideracoes;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public boolean isRecomendacao() {
        return recomendacao;
    }

    public void setRecomendacao(boolean recomendacao) {
        this.recomendacao = recomendacao;
    }

    @Override
    public String toString() {
        return nome + " - " + estilo + " (IBU: " + ibu + ", ABV: " + abv + ")" +
                (recomendacao ? " - Recomendado" : "");
    }
}
