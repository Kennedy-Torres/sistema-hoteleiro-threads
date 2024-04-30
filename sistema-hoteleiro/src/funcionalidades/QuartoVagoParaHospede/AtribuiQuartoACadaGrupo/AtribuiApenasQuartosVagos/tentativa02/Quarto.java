package funcionalidades.QuartoVagoParaHospede.AtribuiQuartoACadaGrupo.AtribuiApenasQuartosVagos.tentativa02;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// 1 quarto -> máx. 4 hospedes
// 1 quarto -> 1 chave
// total de quartos = 10
public class Quarto {
    private int numeroQuarto; // mudar o nome para iDDoQuarto
    private List<Hospede> hospedes;
    private boolean disponivel; // mudar o nome para quartoDisponivel ...em relação a reserva -> estar vago ou não
    private Lock lock;

    public Quarto(int numeroQuarto){
        this.numeroQuarto = numeroQuarto;
        this.hospedes = new ArrayList<>();
        this.disponivel = true;
        this.lock = new ReentrantLock();
    }


    public boolean isDisponivel() {
        return disponivel;
    }

    public void adicionarHospede(Hospede hospede) {
        lock.lock();
        try{
            // se o tamanho da lista de hospedes for maior que 4 neste quarto o quarto passa a  ter o status de indisponível
//            hospedes.add(hospede);
//            if (hospedes.size() >= 4) {disponivel = false;}
            hospedes.add(hospede);
            disponivel = false; // em tese o quarto deixa de estar disponível assim que um hóspede é adicionado
        }finally {
            lock.unlock();
        }
    }

    public List<Hospede> getHospedes() {
        return hospedes;
    }

    public int getNumeroQuarto() {
        return numeroQuarto;
    }
}
