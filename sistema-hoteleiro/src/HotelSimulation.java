import java.util.ArrayList;
import java.util.List;

public class HotelSimulation {
  public static void main(String[] args) {
    List<Quarto> quartos = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      quartos.add(new Quarto(i + 1));
    }

    List<Thread> threads = new ArrayList<>();

    // Criando hóspedes
    for (int i = 0; i < 50; i++) {
      threads.add(new Hospede(i + 1, quartos));
    }

    // Criando camareiras
    for (int i = 0; i < 10; i++) {
      threads.add(new Camareira(quartos));
    }

    // Criando recepcionistas
    for (int i = 0; i < 5; i++) {
      threads.add(new Recepcionista(quartos));
    }

    // Iniciando todas as threads
    for (Thread thread : threads) {
      thread.start();
    }
  }
}
