package br.com.visao;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Quarto {
    private boolean ocupado;
    private int capacidade;
    private Lock lock;
    private int numero;
    private boolean chaveNaRecepcao;
    private boolean limpo;

    public Quarto(int capacidade, int numero) {
        this.ocupado = false;
        this.capacidade = capacidade;
        this.lock = new ReentrantLock();
        this.numero = numero;
        this.chaveNaRecepcao = true;
        this.limpo = false;
    }
    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }
    public boolean isLimpo() {
        return limpo;
    }

    public void setLimpo(boolean limpo) {
        this.limpo = limpo;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public Lock getLock() {
        return lock;
    }

    public int getNumero() {
        return numero;
    }

    public boolean isChaveNaRecepcao() {
        return chaveNaRecepcao;
    }

    public void setChaveNaRecepcao(boolean chaveNaRecepcao) {
        this.chaveNaRecepcao = chaveNaRecepcao;
    }
}
