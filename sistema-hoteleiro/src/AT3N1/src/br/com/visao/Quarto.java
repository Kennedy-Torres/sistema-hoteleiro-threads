package br.com.visao;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Quarto {
    private boolean ocupado;
    private int capacidade;
    private Lock lock;

    public Quarto(int capacidade) {
        this.ocupado = false;
        this.capacidade = capacidade;
        this.lock = new ReentrantLock();
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public Lock getLock() {
        return lock;
    }
}
