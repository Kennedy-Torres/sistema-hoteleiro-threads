package br.com.visao;
import java.util.Queue;

public class Hospede extends Thread {
    private String nome;
    private Quarto quartoAlocado;

    public Hospede(String nome, Queue<Hospede> filaEspera) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setQuartoAlocado(Quarto quartoAlocado) {
        this.quartoAlocado = quartoAlocado;
    }

    public Quarto getQuartoAlocado() {
        return quartoAlocado;
    }

    @Override
    public void run() {
        System.out.println("Hóspede " + nome + " chegou ao hotel.");
    }
}
