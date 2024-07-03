package ui;

import aplicacao.App;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MenuPrincipal extends JPanel implements ActionListener {

    App app;
    Janela janela;
    JButton botaoAposta;
    JButton botaoSaldo;
    JButton botaoLista;
    JButton botaoApuracao;

    public MenuPrincipal(App app, Janela janela) {
        super();
        this.app = app;
        this.janela = janela;

        JPanel teste = new JPanel();
        teste.setLayout(new BorderLayout());

        JPanel painelPrincipal = new JPanel();
        GridLayout layout = new GridLayout(5, 1);
        layout.setVgap(20);
        painelPrincipal.setLayout(layout);
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(100, 10, 100, 10));
        painelPrincipal.setAlignmentX(CENTER_ALIGNMENT);

        JLabel titulo = new JLabel("   Menu Principal");
        titulo.setFont(new java.awt.Font("Arial", Font.BOLD, 20));
        botaoAposta = new JButton("Nova Aposta");
        botaoAposta.setPreferredSize(new Dimension(100, 50));
        botaoSaldo = new JButton("Verificar Saldo");
        botaoLista = new JButton("Lista de Apostas");
        botaoApuracao = new JButton("Finalizar e apurar sorteio");

        painelPrincipal.add(titulo);
        painelPrincipal.add(botaoAposta);
        painelPrincipal.add(botaoSaldo);
        painelPrincipal.add(botaoLista);
        painelPrincipal.add(botaoApuracao);

        botaoAposta.addActionListener(this);
        botaoSaldo.addActionListener(this);
        botaoLista.addActionListener(this);
        botaoApuracao.addActionListener(this);

        add(painelPrincipal, BorderLayout.CENTER);

    }

    @Override

    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == botaoAposta) {
                String[] escolha = { "Apostador já cadastrador", "Cadastrar novo apostador" };
                int opcao = JOptionPane.showOptionDialog(null, "Precisa criar novo usuário?", "Nova Aposta",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, escolha, escolha[0]);
                if (opcao == 0) {
                    String cpf = pedirCPF();
                    if (cpf != null)
                        gerarAposta(cpf);
                    else
                        return;

                } else {
                    String nome = JOptionPane.showInputDialog("Digite o nome do apostador");
                    if (nome == null)
                        return;
                    String cpf = pedirCPF();
                    if (cpf == null)
                        return;
                    app.cadastrarApostador(nome, cpf);
                    gerarAposta(cpf);

                }
            }
            if (e.getSource() == botaoSaldo) {
                String cpf = JOptionPane.showInputDialog("Digite o CPF do apostador");
                if (cpf == null)
                    return;
                JOptionPane.showMessageDialog(null, app.getSaldo(cpf), "Saldo", JOptionPane.INFORMATION_MESSAGE);
            }
            if (e.getSource() == botaoLista) {
                JOptionPane.showMessageDialog(null, app.listarApostas(), "Lista de Apostas",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            if (e.getSource() == botaoApuracao) {
                boolean temCerteza = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja finalizar o sorteio?",
                        "Finalizar Sorteio", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
                if (temCerteza) {
                    app.finalizarApostas();
                    janela.trocarTelas(2);

                } else {
                    return;
                }
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Erro: Preencha todos os campos", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Função criar o popup para gerar a aposta
    private void gerarAposta(String cpf) {

        JTextField[] campos = new JTextField[5];
        JPanel painelPrincipal = new JPanel(new GridLayout(1, 5));
        for (int i = 0; i < 5; i++) {
            campos[i] = new JTextField(1);
            campos[i].addKeyListener((KeyListener) new KeyAdapter() {
                @Override
                // Função para aceitar apenas números
                public void keyTyped(KeyEvent e) {
                    char c = e.getKeyChar();
                    if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE)
                            || (c == KeyEvent.VK_DELETE))) {
                        getToolkit().beep();
                        e.consume();
                    }
                }
            });
            painelPrincipal.add(campos[i]);

        }
        // Criando o popup com os campos pra digitar a aposta
        String[] escolha = { "Apostar", "Aposta Surpresa" };
        int opcao = JOptionPane.showOptionDialog(null, painelPrincipal, "Escolha os números da aposta",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, escolha, escolha[0]);

        if (opcao == 0) {
            int[] numeros = new int[5];
            for (int i = 0; i < 5; i++) {
                numeros[i] = Integer.parseInt(campos[i].getText());
            }
            app.cadastrarAposta(cpf, numeros);
        } else {
            app.cadastrarApostaSurpresinha(cpf);
        }

    }

    // Função para pedir o CPF do apostador
    private String pedirCPF() {
        String cpf = "";
        while (true) {
            boolean digito = true;
            cpf = JOptionPane.showInputDialog("Digite CPF do apostador (somente os números)");
            if (cpf == null) {
                return null;
            }
            // Verifica se o CPF é composto apenas por números
            for (int i = 0; i < cpf.length(); i++) {
                if (!Character.isDigit(cpf.charAt(i))) {
                    digito = false;
                    break;
                }
            }
            // Verifica se o CPF tem 11 dígitos
            if (digito && cpf.length() == 11) {
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Erro: CPF inválido!", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        return cpf;
    }

}
