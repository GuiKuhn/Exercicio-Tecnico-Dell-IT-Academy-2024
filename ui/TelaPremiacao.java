package ui;

import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import aplicacao.App;

// Tela de premiação do programa
public class TelaPremiacao extends JPanel {

    App app;
    int index;

    public TelaPremiacao(App app, Janela janela) {
        super();
        this.app = app;

        index = 0;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setAutoscrolls(true);
        this.app = app;

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));

        JTextArea texto = new JTextArea("\nPREMIAÇÃO\n\n");
        texto.setLineWrap(true);
        texto.setEditable(false);
        texto.setFont(new Font("Arial", Font.BOLD, 20));
        texto.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(painelPrincipal);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JButton botao = new JButton("Próximo");

        painelPrincipal.add(texto);
        painelPrincipal.add(botao);

        botao.addActionListener(e -> {
            if (app.numeroGanhadores() == 0 && index == 0) {
                texto.append("\nNão houve ganhadores dessa vez :(\n\nTente novamente na próxima!");
                botao.setText("Voltar");
                index++;
            } else if (app.numeroGanhadores() == 0 && index != 0) {
                janela.trocarTelas(1);
                janela.clear();
                app.reiniciar();
            } else {
                if (index >= app.getVencedores().length) {
                    janela.trocarTelas(1);
                    janela.clear();
                    app.reiniciar();

                } else if (index == app.getVencedores().length - 1) {
                    texto.append(app.getVencedores()[index]);
                    texto.append("\n\nContinuem tentando!\n\nAqui na Lotérica Guilherme, a sorte compensa!");
                    botao.setText("Voltar");
                    index++;
                } else {
                    texto.append(app.getVencedores()[index]);
                    index++;
                }
            }
        });

        this.add(scroll);
    }
}
