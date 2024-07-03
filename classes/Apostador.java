package classes;

import java.util.ArrayList;

public class Apostador {

    private int idApostador;
    private String nome;
    private String cpf;
    private boolean ganhador;
    private double premiacaoAtual;
    private double saldo;
    private ArrayList<Aposta> apostas;

    // Classe que representa um apostador
    public Apostador(int idApostador, String nome, String cpf, double saldo) {
        this.idApostador = idApostador;
        this.nome = nome;
        this.cpf = cpf;
        this.ganhador = false;
        this.premiacaoAtual = 0;
        this.saldo = saldo;
        this.apostas = new ArrayList<Aposta>();
    }

    public void addAposta(Aposta aposta) {
        this.apostas.add(aposta);
    }

    public int getIdApostador() {
        return idApostador;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public boolean isGanhador() {
        return ganhador;
    }

    public void setGanhador(boolean ganhador) {
        this.ganhador = ganhador;
    }

    public double getPremiacaoAtual() {
        return premiacaoAtual;
    }

    public void setPremiacaoAtual(double premiacaoAtual) {
        this.premiacaoAtual = premiacaoAtual;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public int apostasSize() {
        return apostas.size();
    }

    public ArrayList<Aposta> getApostas() {
        return apostas;
    }

    public void reiniciar() {
        apostas = new ArrayList<>();
        ganhador = false;
        premiacaoAtual = 0;
    }
}
