package funcionalidades.DHospedeTentaAlugarQuarto;


import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Camareira extends Thread{
    private String nome;
    private List<Quarto> quartos;
    private Lock lock;

    public Camareira(String nome, List<Quarto> quartos) {
        this.nome = nome;
        this.quartos = quartos;
        this.lock = new ReentrantLock();
    }

    @Override
    public void run() {
        while (true) {
            // Escolhe um quarto disponível para limpar
            Quarto quartoParaLimpar = selecionarQuartoParaLimpar();
            // Verifica se a chave está na recepção
            if (quartoParaLimpar != null) {
                limparQuarto(quartoParaLimpar);
            }else{
                System.out.println(nome + " não encontrou quartos disponíveis para limpar. Aguardando...");
                try {
                    Thread.sleep(1000); // Espera 1 segundo antes de tentar novamente
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // conta 20 segundos e encerra o expediente
            try{
                Thread.sleep(20000);
                break;
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    private Quarto selecionarQuartoParaLimpar() {
        for (Quarto quarto : quartos) {
            if (quarto.isChaveNaRecepcao()) { // camareira entra no quarto e retorna o quarto que entrou
                quarto.setChaveNaRecepcao(false); // Remove a chave da recepção
                return quarto;
            }
        }
        System.out.println(nome + " não encontrou quartos disponíveis para limpar. Aguardando...");
        return null;
    }

    private void limparQuarto(Quarto quarto) {
        if (lock.tryLock()) {
            try{
                System.out.println(nome + " está limpando o quarto " + quarto.getNumeroQuarto());
                // Simulação de limpeza do quarto...
                Thread.sleep(/*ThreadLocalRandom.current().nextInt(1000, 5000)*/2000); // Simula o tempo de limpeza entre 1 e 5 segundos
                System.out.println(nome + " terminou de limpar o quarto " + quarto.getNumeroQuarto());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock(); // Libera a trava após a limpeza
            }
        }else{
            System.out.println(nome + " não pôde limpar o quarto " + quarto.getNumeroQuarto() + " porque está sendo limpo por outra camareira.");
        }
    }
}
