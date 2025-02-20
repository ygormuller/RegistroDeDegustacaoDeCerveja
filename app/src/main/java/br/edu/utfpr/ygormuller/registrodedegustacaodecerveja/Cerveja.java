package br.edu.utfpr.ygormuller.registrodedegustacaodecerveja;

public class Cerveja {
    private String nome;
    private String Estilo;
    private int IBU;
    private int ABV;
    private  String Consideracoes;
    private  String Nacionalidade;
    private String Classificacao;

    private boolean recomendacao;

    public Cerveja(String nome, String estilo, int ibu, int abv, boolean recomendacao, String nacionalidade, String consideracoes, int classificacao) {
        this.nome = nome;
        this.Estilo = estilo;
        this.IBU = ibu;
        this.ABV = abv;
        this.recomendacao = recomendacao;
        this.Nacionalidade = nacionalidade;
        this.Consideracoes = consideracoes;
        this.Classificacao = String.valueOf(classificacao);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEstilo() {
        return Estilo;
    }

    public void setEstilo(String estilo) {
        Estilo = estilo;
    }

    public int getIBU() {
        return IBU;
    }

    public void setIBU(int IBU) {
        this.IBU = IBU;
    }

    public int getABV() {
        return ABV;
    }

    public void setABV(int ABV) {
        this.ABV = ABV;
    }

    public String getConsideracoes() {
        return Consideracoes;
    }

    public void setConsideracoes(String consideracoes) {
        Consideracoes = consideracoes;
    }

    public String getNacionalidade() {
        return Nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        Nacionalidade = nacionalidade;
    }

    public String getClassificacao() {
        return Classificacao;
    }

    public void setClassificacao(String classificacao) {
        Classificacao = classificacao;
    }

    public boolean isRecomendacao() {
        return recomendacao;
    }

    @Override
    public String toString() {
        return nome + " - " + Estilo + " (IBU: " + IBU + ", ABV: " + ABV + ")" + recomendacao;
    }
}
