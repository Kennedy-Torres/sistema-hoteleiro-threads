package funcionalidades.QuartoVagoParaHospede;

import java.util.ArrayList;
import java.util.List;

public class SistemaHoteleiro {
    public static void main(String[] args) {

        List<Quarto> quartos = new ArrayList<>();
        for (int i = 1; i <= 10; i++) { // 10 quartos no total
            quartos.add(new Quarto(i)); // Capacidade máxima de 4 hóspedes por quarto
        }


        // Criando uma lista de grupos de hóspedes
        List<GrupoHospedes> grupos = new ArrayList<>();
        GrupoHospedes grupo = new GrupoHospedes();

        grupo.adicionarHospede(new Hospede("John"));
        grupo.adicionarHospede(new Hospede("Jane"));
        grupo.adicionarHospede(new Hospede("Bob"));
        grupo.adicionarHospede(new Hospede("jay"));
        grupo.adicionarHospede(new Hospede("Leo"));
        grupo.adicionarHospede(new Hospede("Leo2"));
        grupo.adicionarHospede(new Hospede("Leo3"));
        grupo.adicionarHospede(new Hospede("Leo4"));
        grupo.adicionarHospede(new Hospede("Leo5"));
        grupos.add(grupo);
        // Adicionando mais grupos de hóspedes, se necessário...


        List<Recepcionista> recepcionistas = new ArrayList<>();
        for (int i = 1; i <= 5; i++) { // 5 recepcionistas
            recepcionistas.add(new Recepcionista("Recepcionista " + i, quartos, grupos));
        }

        for (Recepcionista recepcionista : recepcionistas) {
            recepcionista.start();
        }
    }
}
