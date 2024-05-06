import funcionalidades.DHospedeTentaAlugarQuarto.Camareira;
import funcionalidades.DHospedeTentaAlugarQuarto.GrupoHospedes;
import funcionalidades.DHospedeTentaAlugarQuarto.Hospede;
import funcionalidades.DHospedeTentaAlugarQuarto.Quarto;
import funcionalidades.DHospedeTentaAlugarQuarto.Recepcionista;

import java.util.ArrayList;
import java.util.List;

public class SistemaHoteleiro {
    public static void main(String[] args) {

        List<Quarto> quartos = new ArrayList<>();
        for (int i = 1; i <= 10; i++) { // 10 quartos no total // ainda falta trabalhamos com a codição de exceção... caso não tenha quartos suficientes para todos os hospedes
            quartos.add(new Quarto(i)); // Capacidade máxima de 4 hóspedes por quarto
        }


        // simulando a situação que teriamos 5 grupos com total:50 hospedes
        // Criando uma lista de grupos de hóspedes -
        List<GrupoHospedes> grupos = new ArrayList<>();
        int contadorDeHospedes;

        GrupoHospedes grupo1 = new GrupoHospedes("Grupo 01");
        for (contadorDeHospedes = 0; contadorDeHospedes <=4 ; contadorDeHospedes++) { // 5 hospedes no grupo 1
            grupo1.adicionarHospedeDentroDoGrupo(new Hospede("Hospede"+(contadorDeHospedes+1)));
        }
        grupos.add(grupo1);

        GrupoHospedes grupo2 = new GrupoHospedes("Grupo 02");
        for (; contadorDeHospedes <=7 ; contadorDeHospedes++) { // 3 hospedes no grupo 2
            grupo2.adicionarHospedeDentroDoGrupo(new Hospede("Hospede"+(contadorDeHospedes+1)));
        }
        grupos.add(grupo2);

        GrupoHospedes grupo3 = new GrupoHospedes("Grupo 03");
        for (; contadorDeHospedes <=27 ; contadorDeHospedes++) { // 20 hospedes no grupo 3
            grupo3.adicionarHospedeDentroDoGrupo(new Hospede("Hospede"+(contadorDeHospedes+1)));
        }
        grupos.add(grupo3);

        GrupoHospedes grupo4 = new GrupoHospedes("Grupo 04"); // 12 hospedes
        for (; contadorDeHospedes <=39 ; contadorDeHospedes++) {
            grupo4.adicionarHospedeDentroDoGrupo(new Hospede("Hospede"+contadorDeHospedes));
        }
        grupos.add(grupo4);

        GrupoHospedes grupo5 = new GrupoHospedes("Grupo 05"); // 10 hospedes
        for (; contadorDeHospedes <=49 ; contadorDeHospedes++) {
            grupo5.adicionarHospedeDentroDoGrupo(new Hospede("Hospede"+contadorDeHospedes));
        }
        grupos.add(grupo5);


        List<Camareira> camareiras = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            camareiras.add(new Camareira("Camareira "+(i+1),quartos));
            camareiras.get(i).start();
        }




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

        // ----------
        // ----------
        // ----------
        /**
         *  Quando os hospedes de um quarto saem do hotel para passear,
         *  devem deixar a chave na recepcao;
         * */
        // "trava para a thread main" para conseguirmos os dados fornecido
        // pela thread recepcionista antes de continuar o código
        for (Recepcionista recepcionista : recepcionistas) {
            try {
                recepcionista.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        // ----------
        grupo1.simularPasseio(quartos.get(0));
        grupo2.simularPasseio(quartos.get(1));
        grupo3.simularPasseio(quartos.get(2));
        grupo4.simularPasseio(quartos.get(3));
        // ----------
        // ----------


    }
}
