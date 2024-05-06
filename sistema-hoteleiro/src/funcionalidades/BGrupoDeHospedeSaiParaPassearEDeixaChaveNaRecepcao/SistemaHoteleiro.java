package funcionalidades.BGrupoDeHospedeSaiParaPassearEDeixaChaveNaRecepcao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SistemaHoteleiro {
    public static void main(String[] args) {

        List<Quarto> quartos = new ArrayList<>();
        for (int i = 1; i <= 10; i++) { // 10 quartos no total // ainda falta trabalhamos com a codição de exceção... caso não tenha quartos suficientes para todos os hospedes
            quartos.add(new Quarto(i)); // Capacidade máxima de 4 hóspedes por quarto
        }


        // Criando uma lista de grupos de hóspedes
        List<GrupoHospedes> grupos = new ArrayList<>();
        GrupoHospedes grupo = new GrupoHospedes("Grupo 01");

        grupo.adicionarHospedeDentroDoGrupo(new Hospede("John"));
        grupo.adicionarHospedeDentroDoGrupo(new Hospede("Jane"));
        grupo.adicionarHospedeDentroDoGrupo(new Hospede("Bob"));
        grupo.adicionarHospedeDentroDoGrupo(new Hospede("jay"));
        grupo.adicionarHospedeDentroDoGrupo(new Hospede("Leo"));
        grupo.adicionarHospedeDentroDoGrupo(new Hospede("Leo2"));
        grupo.adicionarHospedeDentroDoGrupo(new Hospede("Leo3"));
        grupo.adicionarHospedeDentroDoGrupo(new Hospede("Leo4"));
        grupo.adicionarHospedeDentroDoGrupo(new Hospede("Leo5"));
        grupos.add(grupo);
        // Adicionando mais grupos de hóspedes, se necessário...

        GrupoHospedes grupo2 = new GrupoHospedes("Grupo 02");
        grupo2.adicionarHospedeDentroDoGrupo(new Hospede("knd"));
        grupo2.adicionarHospedeDentroDoGrupo(new Hospede("knd2"));
        grupo2.adicionarHospedeDentroDoGrupo(new Hospede("knd3"));
        grupos.add(grupo2);

        GrupoHospedes grupo3 = new GrupoHospedes("Grupo 03");
        grupo3.adicionarHospedeDentroDoGrupo(new Hospede("ama"));
        grupo3.adicionarHospedeDentroDoGrupo(new Hospede("ama2"));
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


        System.out.println("Número de hóspedes no quarto"+quartos.get(1).getNumeroQuarto()+": " +quartos.get(1).getHospedes().size());
        // Simula a saída do grupo para passear
        grupo.simularSaidaParaPassear(quartos.get(1)); // Supondo que o grupo saia do primeiro quarto
        System.out.println("Número de hóspedes(assim que sairam para passear) no quarto"+quartos.get(1).getNumeroQuarto()+": " +quartos.get(1).getHospedes().size());

        // Verifica se a chave está na recepção
        boolean chaveNaRecepcao = quartos.get(1).isChaveNaRecepcao(); // Verifica o primeiro quarto

        if (chaveNaRecepcao) {
            System.out.println("A chave foi devolvida na recepção; ("+grupos.get(1).getNome()+" saiu para passear).");
        } else {
            System.out.println("A chave não foi devolvida na recepção.");
        }

        // Após o passeio, o grupo retorna para o quarto
        grupo.retornarParaQuarto(quartos.get(1));
        chaveNaRecepcao = false;
        if (!chaveNaRecepcao) {
            System.out.println("O "+grupos.get(1).getNome()+" pegou a chave da recepção");
        } else {
            System.out.println("O "+grupos.get(1).getNome()+" não pegou a chave da recepção");
        }
        System.out.println("Número de hóspedes no quarto"+quartos.get(1).getNumeroQuarto()+": " +quartos.get(1).getHospedes().size());
        // ----------
        // ----------
        // ----------


    }
}
