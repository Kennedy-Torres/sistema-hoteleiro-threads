import java.util.List;

public class Camareira extends Thread {
    private List<Quarto> quartos;

    public Camareira(List<Quarto> quartos) {
        this.quartos = quartos;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10000); // camareira verifica a cada 10 segundos
                for (Quarto quarto : quartos) {
                    synchronized (quarto) {
                        if (!quarto.isDisponivel()) {
                            System.out.println("Camareira limpando quarto " + quarto.getNumero());
                            quarto.setDisponivel(true);
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
