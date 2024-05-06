package br.com.visao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;

public class Camareira extends Thread {
    private String nome;
    private List<Quarto> quartos;
    private Set<Quarto> quartosLimpos;
    private Lock lock;

    public Camareira(String nome, List<Quarto> quartos, Lock lock) {
        this.nome = nome;
        this.quartos = quartos;
        this.quartosLimpos = new HashSet<>();
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true) {
            for (Quarto quarto : quartos) {
                if (quarto.isChaveNaRecepcao() && quarto.isOcupado() && !quartosLimpos.contains(quarto) && !quarto.isLimpo()) {
                    quarto.getLock().lock();
                    try {
                        limparQuarto(quarto);
                    } finally {
                        quarto.getLock().unlock();
                    }
                }
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void limparQuarto(Quarto quarto) {
        if (!quarto.isChaveNaRecepcao() || !quarto.isOcupado() || quarto.isLimpo()) {
            return;
        }
        System.out.println("Camareira " + nome + " está limpando o quarto " + quarto.getNumero());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Camareira " + nome + " terminou de limpar o quarto " + quarto.getNumero());
        quartosLimpos.add(quarto);
        quarto.setLimpo(true);
    }
}

