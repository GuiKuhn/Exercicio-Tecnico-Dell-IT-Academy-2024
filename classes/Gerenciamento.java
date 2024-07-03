package classes;

import java.util.ArrayList;
import java.util.LinkedList;

// Classe que gerencia todos os dados internos do sistema
public class Gerenciamento {

    private ArrayList<Apostador> apostadores;
    private ArrayList<Aposta> apostas;
    private LinkedList<Integer> resultado;

    public Gerenciamento() {
        this.apostadores = new ArrayList<Apostador>();
        this.apostas = new ArrayList<Aposta>();
        this.resultado = new LinkedList<Integer>();
    }

    public boolean addApostador(String nome, String cpf, double premiacaoTotal) {

        int index = 0;
        boolean cpfJaCadastrado = false;
        for (Apostador apostador : this.apostadores) {
            if (apostador.getCpf().equals(cpf)) {
                cpfJaCadastrado = true;
                break;
            }
            if (apostador.getNome().compareTo(nome) > 0) {
                index = apostadores.indexOf(apostador);
                break;
            }
            if (apostadores.indexOf(apostador) == apostadores.size() - 1) {
                index = apostadores.size();
                break;
            }

        }
        if (cpfJaCadastrado) {
            return false;
        }
        int id = this.apostadores.size();
        System.out.println(nome);
        Apostador apostador = new Apostador(id, nome, cpf, premiacaoTotal);
        this.apostadores.add(index, apostador);
        return true;

    }

    public boolean addAposta(String cpf, int[] aposta) {

        for (int i = 0; i < 5; i++) {
            if (aposta[i] < 1 || aposta[i] > 50) {
                return false;
            }
            for (int j = i + 1; j < 5; j++) {
                if (aposta[i] == aposta[j]) {
                    return false;
                }
            }
        }

        for (Apostador apostador : this.apostadores) {
            if (apostador.getCpf().equals(cpf)) {
                int id = this.apostas.size() + 1000;
                Aposta novaAposta = new Aposta(id, apostador, aposta);
                apostador.addAposta(novaAposta);
                this.apostas.add(novaAposta);
                return true;
            }
        }

        return false;
    }

    public boolean addSurpresinha(String cpf) {
        int[] aposta = new int[5];
        for (int i = 0; i < 5; i++) {

            aposta[i] = (int) (Math.random() * 49 + 1);
            aposta[i] = surpresinhaSemRepetidos(aposta, i);
        }
        return addAposta(cpf, aposta);
    }

    private int surpresinhaSemRepetidos(int[] aposta, int i) {
        for (int j = 0; j < i; j++) {
            if (aposta[j] == aposta[i]) {
                aposta[i] = (int) (Math.random() * 49 + 1);
                surpresinhaSemRepetidos(aposta, i);
            }
        }
        return aposta[i];

    }

    private int sorteioSemRepetidos() {
        int numero = (int) (Math.random() * 49 + 1);
        for (int i = 0; i < resultado.size(); i++) {
            if (resultado.get(i) == numero) {
                numero = (int) (Math.random() * 49 + 1);
                sorteioSemRepetidos();
            }
        }
        return numero;
    }

    public boolean finalizarSorteio() {
        if (qtdGanhadores() > 0)
            return true;
        resultado.add(sorteioSemRepetidos());
        int ganhadores = 0;
        LinkedList<Apostador> ganhadoresApostas = new LinkedList<Apostador>();
        for (Aposta aposta : this.apostas) {
            int acertos = 0;
            for (int i = 0; i < 5; i++) {
                if (resultado.contains(aposta.getAposta()[i])) {
                    acertos++;
                }
            }
            if (acertos == 5) {
                ganhadoresApostas.add(aposta.getApostador());
                aposta.setGanhadora(true);
                ganhadores++;
            }
        }
        if (ganhadores > 0) {
            double premiacao = 1000000 / ganhadores;
            double sobra = 1000000;
            for (Apostador apostador : ganhadoresApostas) {
                apostador.setGanhador(true);
                System.out.println(apostador.apostasSize());
                System.out.println(premiacao / apostador.apostasSize());
                double premiacaoIndividual = premiacao / apostador.apostasSize();
                apostador.setPremiacaoAtual(premiacaoIndividual);
                apostador.setSaldo(apostador.getSaldo() + premiacaoIndividual);
                sobra -= premiacaoIndividual;
            }
            sobra /= ganhadores;
            for (Apostador apostador : ganhadoresApostas) {
                apostador.setSaldo(apostador.getSaldo() + sobra);
                apostador.setPremiacaoAtual(apostador.getPremiacaoAtual() + sobra);
            }
        }

        return ganhadores > 0;

    }

    public ArrayList<Integer[]> apostasPorNumero() {
        ArrayList<Integer[]> apostasPorNumero = new ArrayList<Integer[]>();
        for (int i = 0; i < apostas.size(); i++) {
            for (int j = 0; j < 5; j++) {
                boolean novo = true;
                for (int l = 0; l < apostasPorNumero.size(); l++) {
                    if (apostasPorNumero.get(l)[0].equals(apostas.get(i).getAposta()[j])) {
                        apostasPorNumero.get(l)[1] = apostasPorNumero.get(l)[1] + 1;
                        novo = false;
                        break;
                    }
                }
                if (novo) {
                    Integer novaAposta[] = new Integer[2];
                    novaAposta[0] = apostas.get(i).getAposta()[j];
                    novaAposta[1] = 1;
                    apostasPorNumero.add(novaAposta);
                }
            }
        }

        for (int i = 0; i < apostasPorNumero.size(); i++) {
            for (int j = i; j < apostasPorNumero.size(); j++) {
                if (apostasPorNumero.get(i)[1] < apostasPorNumero.get(j)[1]) {
                    Integer[] aux = apostasPorNumero.get(i);
                    apostasPorNumero.set(i, apostasPorNumero.get(j));
                    apostasPorNumero.set(j, aux);
                }
            }
        }
        return apostasPorNumero;
    }

    public int qtdGanhadores() {
        int qtd = 0;
        for (Apostador apostador : apostadores) {
            if (apostador.isGanhador()) {
                qtd++;
            }
        }
        return qtd;
    }

    public void listApotadores() {
        for (Apostador apostador : apostadores) {
            System.out.println(apostador.getNome() + " - " + apostador.getCpf() + " - " + apostador.getSaldo());
        }
    }

    public LinkedList<Integer> getResultado() {
        LinkedList<Integer> aux = resultado;
        return aux;
    }

    public ArrayList<Apostador> getApostadores() {
        ArrayList<Apostador> aux = apostadores;
        return aux;
    }

    public ArrayList<Aposta> getApostas() {
        ArrayList<Aposta> aux = apostas;
        return aux;
    }

    public int getId(String cpf) {
        for (Apostador apostador : apostadores) {
            if (apostador.getCpf().equals(cpf)) {
                return apostador.getIdApostador();
            }
        }
        return -1;
    }

    public LinkedList<Apostador> getGanhadores() {
        LinkedList<Apostador> ganhadores = new LinkedList<>();
        for (Apostador apostador : apostadores) {
            if (apostador.isGanhador()) {
                ganhadores.add(apostador);
            }
        }
        return ganhadores;
    }

    public void reiniciar() {
        apostas = new ArrayList<>();
        resultado = new LinkedList<>();
        for (Apostador apostador : apostadores) {
            apostador.reiniciar();
        }
    }
}
