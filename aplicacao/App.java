package aplicacao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Scanner;

import classes.Aposta;
import classes.Apostador;
import classes.Gerenciamento;

// Classe principal que gerencia a comunicação entre a interface e as classes internas
public class App {

    private Gerenciamento gerenciamento;

    public App() {
        this.gerenciamento = new Gerenciamento();
    }

    // Função que pega os dados do arquivo CSV e guarda na lista interna
    public void iniciar() {
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get("./dados/DADOS-APOSTADORES.csv"),
                    Charset.defaultCharset());
            reader.readLine();
            String linha;
            Scanner scanner;
            while ((linha = reader.readLine()) != null) {
                if (linha.equals(""))
                    break;
                linha = linha.trim();
                scanner = new Scanner(linha).useLocale(Locale.ENGLISH);
                scanner.useDelimiter(";");
                String nome = scanner.next();
                String cpf = scanner.next();
                double saldo = scanner.nextDouble();
                scanner.close();
                if (!gerenciamento.addApostador(nome, cpf, saldo)) {
                    throw new RuntimeException("Apostador de CPF " + cpf + " já cadastrado!");
                }

            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler dados de apostadores");
        }
    }

    public void cadastrarApostador(String nome, String cpf) {
        if (!gerenciamento.addApostador(nome, cpf, 0)) {
            throw new RuntimeException("Apostador já cadastrado!");
        } else {
            try {
                PrintWriter writer = new PrintWriter(
                        Files.newBufferedWriter(Paths.get("./dados/DADOS-APOSTADORES.csv"), Charset.defaultCharset()));
                writer.println("Nome;CPF;Saldo");
                for (Apostador a : gerenciamento.getApostadores()) {
                    writer.println(a.getNome() + ";" + a.getCpf() + ";" + a.getSaldo());
                }
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException("Erro em achar árquivo CSV");
            }
        }
    }

    public void cadastrarAposta(String cpf, int[] aposta) {

        for (int i = 0; i < 5; i++) {
            if (aposta[i] < 1 || aposta[i] > 50) {
                throw new RuntimeException("Digite números entre 1 e 50!");
            }
            for (int j = i + 1; j < 5; j++) {
                if (aposta[i] == aposta[j]) {
                    throw new RuntimeException("Não é permitido repetir números!");
                }
            }
        }

        if (!gerenciamento.addAposta(cpf, aposta)) {
            throw new RuntimeException("CPF não cadastrado");
        }
    }

    public void cadastrarApostaSurpresinha(String cpf) {

        if (!gerenciamento.addSurpresinha(cpf)) {
            throw new RuntimeException("CPF não cadastrado!");
        }
    }

    public boolean finalizarApostas() {

        if (gerenciamento.getApostas().size() == 0) {
            throw new RuntimeException("Nenhuma aposta cadastrada!");
        }
        int index = 0;
        while (index <= 29 && !gerenciamento.finalizarSorteio()) {
            index++;
        }
        if (index == 30) {
            return false;
        }

        try {
            PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Paths.get("./dados/DADOS-APOSTADORES.csv"),
                    Charset.defaultCharset()));
            writer.println("Nome;CPF;Saldo");
            for (Apostador a : gerenciamento.getApostadores()) {
                writer.println(a.getNome() + ";" + a.getCpf() + ";" + a.getSaldo());
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Erro em achar árquivo CSV");
        }

        return true;
    }

    public String listarResultado() {
        LinkedList<Integer> resultado = gerenciamento.getResultado();
        String aux = "Os números sorteados foram: ";
        for (int i = 0; i < resultado.size(); i++) {
            aux += resultado.get(i);
            if (i != resultado.size() - 1) {
                aux += ", ";
            } else {
                aux += ".";
            }
        }

        return aux;
    }

    public int numeroGanhadores() {
        return gerenciamento.qtdGanhadores();
    }

    public int qtdSorteios() {
        return gerenciamento.getResultado().size() - 4;
    }

    public String listaApostasPorNumero() {
        ArrayList<Integer[]> apostaPorNumeros = gerenciamento.apostasPorNumero();
        String aux = "Número - Quantidade\n";
        for (int i = 0; i < apostaPorNumeros.size(); i++) {
            aux += apostaPorNumeros.get(i)[0] + " - " + apostaPorNumeros.get(i)[1] + "\n";
        }
        return aux;
    }

    public String listarApostas() {
        String aux = "";

        if (gerenciamento.getApostas().size() == 0) {
            throw new RuntimeException("Nenhuma aposta cadastrada!");
        }

        for (int i = 0; i < gerenciamento.getApostas().size(); i++) {
            Aposta aposta = gerenciamento.getApostas().get(i);
            aux += "Id: " + aposta.getId() + " - Apostador: " + aposta.getApostador().getNome()
                    + " - Números: ";
            for (int j = 0; j < aposta.getAposta().length; j++) {
                aux += aposta.getAposta()[j];
                if (j < 4) {
                    aux += ", ";
                } else {
                    aux += "\n";
                }
            }
        }

        return aux;
    }

    public String listarApostasVencedoras() {
        String aux = "";
        for (int i = 0; i < gerenciamento.getApostadores().size(); i++) {
            Apostador apostador = gerenciamento.getApostadores().get(i);
            if (apostador.isGanhador()) {
                aux += "Apostador: " + apostador.getNome();
                for (int j = 0; j < apostador.getApostas().size(); j++) {
                    Aposta aposta = apostador.getApostas().get(j);
                    if (aposta.isGanhadora()) {
                        aux += " - Id: " + aposta.getId() + " - Números: ";
                        for (int k = 0; k < 5; k++) {
                            aux += aposta.getAposta()[k];
                            if (k < 4) {
                                aux += ", ";
                            } else {
                                aux += "\n";
                            }
                        }
                    }
                }
            }
        }
        return aux;
    }

    public String getSaldo(String cpf) {

        String aux = null;
        for (int i = 0; i < gerenciamento.getApostadores().size(); i++) {
            if (gerenciamento.getApostadores().get(i).getCpf().equals(cpf)) {
                aux = "Saldo: " + gerenciamento.getApostadores().get(i).getSaldo();
            }
        }

        if (aux == null) {
            throw new RuntimeException("Apostador não encontrado!");
        } else {
            return aux;
        }
    }

    public String[] getVencedores() {

        String[] vencedores = new String[gerenciamento.qtdGanhadores()];
        int index = 0;
        for (int i = 0; i < gerenciamento.getApostadores().size(); i++) {
            Apostador apostador = gerenciamento.getApostadores().get(i);
            if (apostador.isGanhador()) {
                vencedores[index] = apostador.getNome() + ", VOCÊ GANHOU R$"
                        + String.format("%.2f", apostador.getPremiacaoAtual())
                        + "! AGORA SEU SALDO É DE R$"
                        + String.format("%.2f", apostador.getSaldo()) + "!\n";
                index++;
            }
        }
        return vencedores;
    }

    // Função que reinicia o sistema
    public void reiniciar() {
        gerenciamento.reiniciar();
    }

}
