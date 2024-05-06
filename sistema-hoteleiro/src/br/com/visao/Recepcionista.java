package br.com.visao;

import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.List;

public class Recepcionista extends Thread {
    private String nome;
    private Queue<Hospede> filaEspera;
    private List<Quarto> quartos;
    private Lock lock;

    public Recepcionista(String nome, Queue<Hospede> filaEspera, List<Quarto> quartos, Lock lock) {
        this.nome = nome;
        this.filaEspera = filaEspera;
        this.quartos = quartos;
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true) {
            Hospede hospede;
            synchronized (filaEspera) {
                hospede = filaEspera.poll();
            }
            if (hospede != null) {
                Quarto quartoDisponivel = procurarQuartoDisponivel(hospede);
                if (quartoDisponivel != null) {
                    realizarCheckIn(hospede, quartoDisponivel);
                } else {
                    filaEspera.offer(hospede);
                    System.out.println("Hóspede " + hospede.getNome() + " está saindo para passear.");
                    realizarCheckOut(hospede);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Hóspede " + hospede.getNome() + " retornou ao hotel.");
                }
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Quarto procurarQuartoDisponivel(Hospede hospede) {
        for (Quarto quarto : quartos) {
            if (!quarto.isOcupado() && quarto.isChaveNaRecepcao() && hospede.getQuartoAlocado() == null) {
                if (quarto.getCapacidade() >= hospede.getGrupoSize()) {
                    return quarto;
                }
            }
        }
        return null;
    }

    private void realizarCheckIn(Hospede hospede, Quarto quarto) {
        lock.lock();
        try {
            System.out.println("Recepcionista " + nome + " realizou o check-in para Hóspede " + hospede.getNome());
            quarto.setOcupado(true);
            quarto.setChaveNaRecepcao(true);
            hospede.setQuartoAlocado(quarto);
        } finally {
            lock.unlock();
        }
    }



    private void realizarCheckOut(Hospede hospede) {
        Quarto quarto = hospede.getQuartoAlocado();
        if (quarto != null) {
            lock.lock();
            try {
                System.out.println("Recepcionista " + nome + " realizou o check-out para Hóspede " + hospede.getNome());
                quarto.setOcupado(false);
                quarto.setChaveNaRecepcao(true);
            } finally {
                lock.unlock();
            }
        }
    }

}

