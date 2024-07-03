package ui;

import aplicacao.App;
import javax.swing.JFrame;

public class Janela extends JFrame {

    App app;
    MenuInicial menuInicial;
    MenuPrincipal menuPrincipal;
    TelaApuracao telaApuracao;
    TelaPremiacao telaPremiacao;

    // Frame que contém as telas do programa
    public Janela() {
        super("Lotérica Guilherme");
        app = new App();
        menuInicial = new MenuInicial(app, this);
        menuPrincipal = new MenuPrincipal(app, this);
        telaApuracao = new TelaApuracao(app, this);
        telaPremiacao = new TelaPremiacao(app, this);

        add(menuInicial);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

    // Função que troca a tela atual
    public void trocarTelas(int tela) {
        switch (tela) {
            case 1:
                this.setContentPane(menuPrincipal);
                break;
            case 2:
                this.setContentPane(telaApuracao);
                break;
            case 3:
                this.setContentPane(telaPremiacao);
                break;
        }
        this.validate();
    }

    // Função que limpa as telas
    public void clear() {
        menuInicial = new MenuInicial(app, this);
        menuPrincipal = new MenuPrincipal(app, this);
        telaApuracao = new TelaApuracao(app, this);
        telaPremiacao = new TelaPremiacao(app, this);
    }

}
