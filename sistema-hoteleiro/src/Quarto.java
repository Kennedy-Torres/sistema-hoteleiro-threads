import funcionalidades.DHospedeTentaAlugarQuarto.GrupoHospedes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// 1 quarto -> máx. 4 hospedes
// 1 quarto -> 1 chave
// total de quartos = 10
public class Quarto {
    private int numeroQuarto; // mudar o nome para iDDoQuarto
    private List<GrupoHospedes> grupos;
    private boolean disponivel; // mudar o nome para quartoDisponivel ...em relação a reserva -> estar vago ou não
    private Lock lock;
    private boolean chaveNaRecepcao = true;
    private boolean TemGenteDentroDoQuarto = false;

    public Quarto(int numeroQuarto){
        this.numeroQuarto = numeroQuarto;
        this.grupos = new ArrayList<>();
        this.disponivel = true;
        this.lock = new ReentrantLock();
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    // INSERE o grupo/familia DENTRO DE OUTRA LISTA{seus elementos são grupos}
    public void adicionarGrupoDeHospedes(GrupoHospedes grupo) { // lista com varios grupos
        lock.lock();
        try{
            grupos.add(grupo);
            // Atualizamos o status de disponibilidade do quarto apenas se estava vazio antes de adicionar o grupo
            if (grupos.size() >= 1) {
                disponivel = false; // O quarto deixa de estar disponível assim que um grupo é adicionado
                chaveNaRecepcao = false; // o quarto é entrege ao grupo de hospedes logo a chave não está mais na recepção
                TemGenteDentroDoQuarto = true;
            }
        }finally {
            lock.unlock();
        }
    }

    public void removerGrupoDeHospedes(GrupoHospedes grupo) {
        lock.lock();
        try {
            grupos.remove(grupo);
            if (grupos.isEmpty()) {
                disponivel = true;
            }
        } finally {
            lock.unlock();
        }
    }


    public void devolverChaveNaRecepcao() {
        if (!TemGenteDentroDoQuarto) { // Se não há hóspedes no quarto ... camareira entra
            chaveNaRecepcao = true; // Marca que a chave está na recepção
//            System.out.println("Chave foi devolvida na recepção).");
            System.out.println("Chave do quarto " + numeroQuarto + " foi devolvida na recepção.");
        }
    }

    // Método para pegar a chave da recepção
    public void pegarChaveDaRecepcao() {
        chaveNaRecepcao = false; // Marca que a chave não está mais na recepção
        TemGenteDentroDoQuarto = true;
    }

    public List<GrupoHospedes> getGrupos() {
        return grupos;
    }

    public int getNumeroQuarto() {
        return numeroQuarto;
    }

    public boolean isChaveNaRecepcao() { // getter
        return chaveNaRecepcao;
    }

    public void setChaveNaRecepcao(boolean chaveNaRecepcao) {
        this.chaveNaRecepcao = chaveNaRecepcao;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public Lock getLock() {
        return lock;
    }

    public void setTemGenteDentroDoQuarto(boolean temGenteDentroDoQuarto) {
        TemGenteDentroDoQuarto = temGenteDentroDoQuarto;
    }
}

