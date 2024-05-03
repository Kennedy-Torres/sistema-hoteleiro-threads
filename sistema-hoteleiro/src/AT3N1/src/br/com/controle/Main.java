package br.com.controle;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import br.com.visao.Camareira;
import br.com.visao.Hospede;
import br.com.visao.Quarto;
import br.com.visao.Recepcionista;


public class Main {
	public static void main(String[] args) {
        final int NUM_QUARTOS = 10;
        final int CAPACIDADE_QUARTO = 4;

        List<Quarto> quartos = new ArrayList<>();
        for (int i = 0; i < NUM_QUARTOS; i++) {
            quartos.add(new Quarto(CAPACIDADE_QUARTO));
        }

        Queue<Hospede> filaEspera = new ArrayBlockingQueue<>(50);

        List<Recepcionista> recepcionistas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            recepcionistas.add(new Recepcionista("" + (i + 1), filaEspera, quartos));
        }

        List<Camareira> camareiras = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            camareiras.add(new Camareira("" + (i + 1), quartos));
        }

        for (Recepcionista recepcionista : recepcionistas) {
            recepcionista.start();
        }

        for (Camareira camareira : camareiras) {
            camareira.start();
        }

        for (int i = 0; i < 50; i++) {
            Hospede hospede = new Hospede("" + (i + 1), filaEspera);
            filaEspera.offer(hospede);
            try {
                Thread.sleep(100); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
