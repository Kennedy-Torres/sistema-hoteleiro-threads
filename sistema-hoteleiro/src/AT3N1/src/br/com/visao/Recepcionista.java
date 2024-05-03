package br.com.visao;

import java.util.Queue;
import java.util.List;

public class Recepcionista extends Thread {
    private String nome;
    private Queue<Hospede> filaEspera;
    private List<Quarto> quartos;

    public Recepcionista(String nome, Queue<Hospede> filaEspera, List<Quarto> quartos) {
        this.nome = nome;
        this.filaEspera = filaEspera;
        this.quartos = quartos;
    }

    @Override
    public void run() {
        while (true) {
            Hospede hospede;
            synchronized (filaEspera) {
                hospede = filaEspera.poll();
            }
            if (hospede != null) {
                Quarto quartoDisponivel = procurarQuartoDisponivel();
                if (quartoDisponivel != null) {
                    realizarCheckIn(hospede, quartoDisponivel);
                } else {
                    // Caso não haja quartos disponíveis, o hóspede volta para a fila de espera
                    filaEspera.offer(hospede);
                }
            }
            try {
                Thread.sleep(2000); // Aguarda um pouco antes de verificar novamente a fila
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Quarto procurarQuartoDisponivel() {
        for (Quarto quarto : quartos) {
            if (!quarto.isOcupado()) {
                return quarto;
            }
        }
        return null;
    }

    private void realizarCheckIn(Hospede hospede, Quarto quarto) {
        quarto.getLock().lock();
        try {
            System.out.println("Recepcionista " + nome + " realizou o check-in para Hóspede " + hospede.getNome());
            hospede.setQuartoAlocado(quarto);
            quarto.setOcupado(true);
        } finally {
            quarto.getLock().unlock();
        }
    }
}

