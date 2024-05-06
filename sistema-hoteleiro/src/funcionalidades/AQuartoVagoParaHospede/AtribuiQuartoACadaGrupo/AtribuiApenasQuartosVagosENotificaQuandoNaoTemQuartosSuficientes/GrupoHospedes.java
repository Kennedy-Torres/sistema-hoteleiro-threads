package funcionalidades.AQuartoVagoParaHospede.AtribuiQuartoACadaGrupo.AtribuiApenasQuartosVagosENotificaQuandoNaoTemQuartosSuficientes;

import java.util.ArrayList;
import java.util.List;

public class GrupoHospedes {
    private String nome;
    private List<Hospede> hospedes;

    public GrupoHospedes(String nome) {
        this.nome = nome;
        this.hospedes = new ArrayList<>();
    }

    public void adicionarHospedeDentroDoGrupo(Hospede hospede) {
        hospedes.add(hospede);
    }

    public int getTamanho() {
        return hospedes.size();
    }

    public List<Hospede> getHospedes() {
        return hospedes;
    }

    public String getNome() {
        return nome;
    }
}