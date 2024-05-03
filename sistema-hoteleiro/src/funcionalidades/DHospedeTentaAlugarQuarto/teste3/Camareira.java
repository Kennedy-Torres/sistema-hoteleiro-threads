package funcionalidades.DHospedeTentaAlugarQuarto.teste3;

public class Camareira extends Thread{
    private String nome;
    private Quarto quarto;

    public Camareira(String nome, Quarto quarto) {
        this.nome = nome;
        this.quarto = quarto;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Verifica se a chave está na recepção
                if (quarto.isChaveNaRecepcao()) {
                    // Entra no quarto
                    synchronized (quarto) {
                        quarto.pegarChaveDaRecepcao(); // Marca a chave como não disponível na recepção
                        quarto.setTemGenteDentroDoQuarto(true); // Marca que há hóspedes dentro do quarto
                        System.out.println("Camareira " + nome + " realiza a limpeza do quarto " + quarto.getNumeroQuarto() + ".");
                        Thread.sleep(2000); // Simula o tempo necessário para limpar o quarto
                        quarto.setTemGenteDentroDoQuarto(false); // Marca que não há mais hóspedes dentro do quarto
                    }
                }
                Thread.sleep(1000); // Espera um segundo antes de verificar novamente
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
