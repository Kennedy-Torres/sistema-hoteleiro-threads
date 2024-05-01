import java.util.List;

public class Recepcionista extends Thread {
    private List<Quarto> quartos;

    public Recepcionista(List<Quarto> quartos) {
        this.quartos = quartos;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(2000); // recepcionista verifica a cada 2 segundos
                int quartosDisponiveis = 0;
                for (Quarto quarto : quartos) {
                    synchronized (quarto) {
                        if (quarto.isDisponivel()) {
                            quartosDisponiveis++;
                        }
                    }
                }
                System.out.println("Número de quartos disponíveis: " + quartosDisponiveis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
