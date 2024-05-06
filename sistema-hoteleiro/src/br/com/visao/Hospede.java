package br.com.visao;

public class Hospede extends Thread {
    private String nome;
    private Quarto quartoAlocado;
    private int grupoSize;
    private int tentativas;

    public Hospede(String nome) {
        this.nome = nome;
        this.tentativas = 0;
    }

    public String getNome() {
        return nome;
    }

    public void setQuartoAlocado(Quarto quartoAlocado) {
        this.quartoAlocado = quartoAlocado;
    }

    public Quarto getQuartoAlocado() {
        return quartoAlocado;
    }

    public int getGrupoSize() {
        return grupoSize;
    }

    public void setGrupoSize(int grupoSize) {
        this.grupoSize = grupoSize;
    }

    @Override
    public void run() {
        System.out.println("Hóspede " + nome + " chegou ao hotel.");
        while (tentativas < 2) {
            if (quartoAlocado != null) {
                if (quartoAlocado.isChaveNaRecepcao()) {
                    quartoAlocado.setChaveNaRecepcao(false);
                    System.out.println("Hóspede " + nome + " deixou a chave do quarto " + quartoAlocado.getNumero() + " na recepção.");
                    break;
                }
            } else {
                tentativas++;
                System.out.println("Hóspede " + nome + " esperando por um quarto (" + tentativas + "ª tentativa)...");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (tentativas >= 2) {
            System.out.println("Hóspede " + nome + " deixou uma reclamação e saiu.");
        } else {
            passearPelaCidade();
            System.out.println("Hóspede " + nome + " retornou ao hotel após passeio.");
        }
    }

    private void passearPelaCidade() {
        System.out.println("Hóspede " + nome + " está saindo para passear pela cidade.");
        try {
            Thread.sleep((int) (Math.random() * 10000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
