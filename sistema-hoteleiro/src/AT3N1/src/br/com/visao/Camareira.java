package br.com.visao;
import java.util.List;

public class Camareira extends Thread {
    private String nome;
    private List<Quarto> quartos;

    public Camareira(String nome, List<Quarto> quartos) {
        this.nome = nome;
        this.quartos = quartos;
    }

    @Override
    public void run() {
        while (true) {
            for (Quarto quarto : quartos) {
                if (quarto.isOcupado()) {
                    quarto.getLock().lock();
                    try {
                        limparQuarto(quarto);
                    } finally {
                        quarto.getLock().unlock();
                    }
                }
            }
            try {
                Thread.sleep(3000); // Aguarda um pouco antes de verificar novamente os quartos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void limparQuarto(Quarto quarto) {
        if (!quarto.isOcupado()) {
            return; // Retorna se o quarto estiver vago
        }
        System.out.println("Camareira " + nome + " está limpando o quarto.");
        try {
            Thread.sleep(2000); // Simula o tempo de limpeza do quarto
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Camareira " + nome + " terminou de limpar o quarto.");
        quarto.setOcupado(false);
    }
}

