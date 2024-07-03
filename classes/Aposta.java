package classes;

public class Aposta {
    private int id;
    private Apostador apostador;
    private boolean ganhadora;
    private int aposta[];

    public Aposta(int id, Apostador apostador, int[] aposta) {
        this.id = id;
        this.apostador = apostador;
        this.aposta = aposta;
        this.ganhadora = false;
    }

    public int getId() {
        return id;
    }

    public Apostador getApostador() {
        return apostador;
    }

    public int[] getAposta() {
        return aposta;
    }

    public boolean isGanhadora() {
        return ganhadora;
    }

    public void setGanhadora(boolean ganhadora) {
        this.ganhadora = ganhadora;
    }
}
