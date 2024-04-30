package funcionalidades.QuartoVagoParaHospede;

import java.util.ArrayList;
import java.util.List;

public class GrupoHospedes {
    private List<Hospede> hospedes;

    public GrupoHospedes() {
        this.hospedes = new ArrayList<>();
    }

    public void adicionarHospede(Hospede hospede) {
        hospedes.add(hospede);
    }

    public int getTamanho() {
        return hospedes.size();
    }

    public List<Hospede> getHospedes() {
        return hospedes;
    }
}
