package ui;

import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import aplicacao.App;

// Tela de apuração de sorteio
public class TelaApuracao extends JPanel {

    App app;
    int index;

    public TelaApuracao(App app, Janela janela) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setAutoscrolls(true);
        this.app = app;
        index = 0;

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));

        JTextArea texto = new JTextArea("\nAPURAÇÃO DE SORTEIO\n\n");
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
            if (index == 0) {
                texto.append("\n" + app.listarResultado());
                index++;
            } else if (index == 1) {
                texto.append("\nHouve " + app.qtdSorteios() + " sorteio(s) realizado(s)");
                index++;
            } else if (index == 2) {
                texto.append("\nHouve " + app.numeroGanhadores() + " ganhador(es)");
                index++;
            } else if (index == 3) {
                if (app.numeroGanhadores() == 0) {
                    texto.append("\nNão tem apostas vencendoras");
                } else {
                    texto.append("\nApostas vencedoras:\n" + app.listarApostasVencedoras());
                }
                index++;
            } else if (index == 4) {
                texto.append("\n" + app.listaApostasPorNumero());
                index++;
            } else {
                janela.trocarTelas(3);
            }
        });

        this.add(scroll);

    }
}
