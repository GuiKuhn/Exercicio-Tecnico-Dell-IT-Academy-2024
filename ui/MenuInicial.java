package ui;

import aplicacao.App;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

// Tela inicial do programa
public class MenuInicial extends JPanel {

    JButton botaoIniciar;

    public MenuInicial(App app, Janela janela) {
        super();

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new GridLayout(2, 1));

        JPanel painelTextos = new JPanel();
        JPanel painelBotao = new JPanel();

        JLabel titulo = new JLabel("Bem vindo à Lotérica Guilherme");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        JLabel subtitulo = new JLabel("              Onde a sorte compensa!");
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 16));

        botaoIniciar = new JButton("Iniciar");
        botaoIniciar.setPreferredSize(new Dimension(200, 50));
        botaoIniciar.setFont(new Font("Arial", Font.BOLD, 16));

        GridLayout layout = new GridLayout(2, 1);
        layout.setVgap(20);
        painelTextos.setLayout(layout);
        painelTextos.setBorder(BorderFactory.createEmptyBorder(50, 10, 120, 10));

        painelTextos.add(titulo);
        painelTextos.add(subtitulo);
        painelBotao.add(botaoIniciar);

        painelPrincipal.add(painelTextos);
        painelPrincipal.add(painelBotao);

        this.add(painelPrincipal);

        botaoIniciar.addActionListener(e -> {
            janela.trocarTelas(1);
            app.iniciar();
        });
    }
}
