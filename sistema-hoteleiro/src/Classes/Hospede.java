
import java.util.List;
import java.util.Random;

public class Hospede extends Thread {
    private int id;
    private List<Quarto> quartos;

    public Hospede(int id, List<Quarto> quartos) {
        this.id = id;
        this.quartos = quartos;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            try {
                Thread.sleep(random.nextInt(5000)); // tempo de espera aleatório
                int quartoIndex = random.nextInt(quartos.size());
                Quarto quarto = quartos.get(quartoIndex);
                synchronized (quarto) {
                    if (quarto.isDisponivel()) {
                        System.out.println("Hóspede " + id + " reservou quarto " + quarto.getNumero());
                        quarto.setDisponivel(false);
                    } else {
                        System.out.println("Hóspede " + id + " não conseguiu reservar quarto " + quarto.getNumero());
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
