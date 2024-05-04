package br.com.controle;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import br.com.visao.Camareira;
import br.com.visao.Hospede;
import br.com.visao.Quarto;
import br.com.visao.Recepcionista;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Main {
    public static Queue<Hospede> filaEspera;

    public static void main(String[] args) {
        final int NUM_QUARTOS = 10;
        final int CAPACIDADE_QUARTO = 4;

        List<Quarto> quartos = new ArrayList<>();
        for (int i = 0; i < NUM_QUARTOS; i++) {
            quartos.add(new Quarto(CAPACIDADE_QUARTO, i + 1));
        }

        filaEspera = new ArrayBlockingQueue<>(50);

        Lock lock = new ReentrantLock();

        List<Recepcionista> recepcionistas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            recepcionistas.add(new Recepcionista("" + (i + 1), filaEspera, quartos, lock));
        }

        List<Camareira> camareiras = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            camareiras.add(new Camareira("" + (i + 1), quartos, lock));
        }

        for (Recepcionista recepcionista : recepcionistas) {
            recepcionista.start();
        }

        for (Camareira camareira : camareiras) {
            camareira.start();
        }

        for (int i = 0; i < 50; i++) {
            Hospede hospede = new Hospede("" + (i + 1));
            int grupoSize = (int) (Math.random() * 8) + 1;
            hospede.setGrupoSize(grupoSize);
            filaEspera.offer(hospede);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}