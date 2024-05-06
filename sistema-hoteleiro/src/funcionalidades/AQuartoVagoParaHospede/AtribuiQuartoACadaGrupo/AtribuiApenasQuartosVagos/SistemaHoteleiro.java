package funcionalidades.AQuartoVagoParaHospede.AtribuiQuartoACadaGrupo.AtribuiApenasQuartosVagos;

import java.util.ArrayList;
import java.util.List;

public class SistemaHoteleiro {
    public static void main(String[] args) {

        List<Quarto> quartos = new ArrayList<>();
        for (int i = 1; i <= 4; i++) { // 10 quartos no total // ainda falta trabalhamos com a codição de exceção... caso não tenha quartos suficientes para todos os hospedes
            quartos.add(new Quarto(i)); // Capacidade máxima de 4 hóspedes por quarto
        }


        // Criando uma lista de grupos de hóspedes
        List<GrupoHospedes> grupos = new ArrayList<>();
        GrupoHospedes grupo = new GrupoHospedes("Grupo 01");

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

        GrupoHospedes grupo2 = new GrupoHospedes("Grupo 02");
        grupo2.adicionarHospede(new Hospede("knd"));
        grupo2.adicionarHospede(new Hospede("knd2"));
        grupos.add(grupo2);

        GrupoHospedes grupo3 = new GrupoHospedes("Grupo 03");
        grupo3.adicionarHospede(new Hospede("ama"));
        grupo3.adicionarHospede(new Hospede("ama2"));
        grupo3.adicionarHospede(new Hospede("ama3"));
        grupos.add(grupo3);
        //...

        List<Recepcionista> recepcionistas = new ArrayList<>();
        /**
        * Iremos atribuir grupos específicos a cada thread de Recepcionista de maneira equilibrada,
         * Podemos fazer isso dividindo a lista de grupos entre as threads de forma que cada thread receba uma
         * porção dos grupos para lidar.
         * */
        int numGrupos = grupos.size();
        int numRecepcionistas = Math.min(numGrupos, 5/*recepcionistas.size()*/); // Limitando o número de recepcionistas ao número de grupos ou 5, o que for menor



        // Cria uma thread de Recepcionista para cada grupo - > cada thread é associada a um grupo específico de hóspedes
        /**
        * 1ºNvl-Paralelismo: Multiplas threads de recepcionista podem ser criadas/inicializadas e
         * cada uma lida com um grupos diferentes de hospedes
        * */
        for (int i = 1; i <= numRecepcionistas; i++) {
            List<GrupoHospedes> gruposParaRecepcionista = new ArrayList<>(); // lista de grupos que a recepcionista está responsável para atender
            gruposParaRecepcionista.add(grupos.get(i - 1)); // Adiciona o grupoDeHospedes que está na lista grupos para a lista de grupos que a recepcionista vai atender
            recepcionistas.add(new Recepcionista("Recepcionista " + i, quartos, gruposParaRecepcionista)); // instancia a classe recepcionista e adiciona ela dentro da lista de recepcionistas
        }

        // starta cada classe recepcionista que foi instânciada
        for (Recepcionista recepcionista : recepcionistas) {
            recepcionista.start();
        }
    }
}
