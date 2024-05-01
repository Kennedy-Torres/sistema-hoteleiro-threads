public class Quarto {
    private int numero;
    private boolean disponivel;

    public Quarto(int numero) {
        this.numero = numero;
        this.disponivel = true;
    }

    public synchronized boolean isDisponivel() {
        return disponivel;
    }

    public synchronized void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public int getNumero() {
        return numero;
    }
}
