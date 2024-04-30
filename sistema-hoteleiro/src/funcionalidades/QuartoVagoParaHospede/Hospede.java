package funcionalidades.QuartoVagoParaHospede;

public class Hospede extends Thread{
    private String nome;

    public Hospede(String nome) {
        this.nome = nome;
    }

    @Override
    public void run() {
        System.out.println("Hóspede- " + nome + " chegou ao hotel.");
    }


    public String getNome() {
        return nome;
    }
}
