package funcionalidades.AQuartoVagoParaHospede.AtribuiQuartoACadaGrupo;

import java.util.List;

public class Recepcionista extends Thread{
    private String nome;
    private List<Quarto> quartosDisponiveis;
    private List<GrupoHospedes> grupos;



    public Recepcionista(String nome, List<Quarto> quartosDisponiveis, List<GrupoHospedes> grupos) {
        this.nome = nome;
        this.quartosDisponiveis = quartosDisponiveis;
        this.grupos = grupos;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Quarto quartoDisponivel = procurarQuartoDisponivel();
                if (quartoDisponivel != null) {
                    synchronized (quartoDisponivel) {
                        for (GrupoHospedes grupo : grupos) {
                            if (grupo.getTamanho() <= 4) {
                                alocarGrupo(quartoDisponivel, grupo);
                            } else {
                                // Se o grupo for maior que 4, dividir em grupos menores
                                while (grupo.getTamanho() > 0) {
                                    GrupoHospedes novoGrupo = new GrupoHospedes(grupo.getNome());
                                    for (int i = 0; i < 4 && grupo.getTamanho() > 0; i++) {
                                        novoGrupo.adicionarHospede(grupo.getHospedes().get(0));
                                        grupo.getHospedes().remove(0);
                                    }
                                    alocarGrupo(procurarQuartoDisponivel(), novoGrupo);
                                }
                            }
                        }
                        break; // Uma vez que todos os grupos foram alocados, a tarefa do recepcionista está completa
                    }
                } else {
                    System.out.println("Recepcionista " + nome + " esperando por quartos disponíveis.");
                    Thread.sleep(1000); // Espera um segundo antes de tentar novamente
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private void alocarGrupo(Quarto quarto, GrupoHospedes grupo) {
        System.out.println("Recepcionista- " + nome + " alocando quarto " + quarto.getNumeroQuarto() +
                " para "+ grupo.getNome()+"; (está com " + grupo.getTamanho() + " membros dentro do quarto).");
        List<Hospede> hospedesNoQuarto = quarto.getHospedes();
        for (Hospede hospede : grupo.getHospedes()) {
            if (hospedesNoQuarto.size() < 4) {
                hospedesNoQuarto.add(hospede);
            } else {
                break;
            }
        }
        if (hospedesNoQuarto.size() >= 4) {
            quarto.adicionarHospede(new Hospede("Grupo"));
        }
    }



    private Quarto procurarQuartoDisponivel() {
        for (Quarto quarto : quartosDisponiveis) {
            if (quarto.isDisponivel()) {
                return quarto;
            }
        }
        return null;
    }


    //    public void realizarCheckIn(int numeroDeHospedesDoGrupo) {
//        int numeroDeQuartosNecessarios = (int) Math.ceil((double) numeroDeHospedesDoGrupo / 4);
//
//        // Verifica se há quartos disponíveis suficientes para acomodar o grupo
//        int quartosDisponiveis = 0;
//        for (Quarto quarto : quartos) {
//            synchronized (quarto) {
//                if (quarto.estaDisponivel()) {
//                    quartosDisponiveis++;
//                }
//            }
//        }
//
//        if (quartosDisponiveis >= numeroDeQuartosNecessarios) {
//            // Alocar o grupo em quartos disponíveis
//            int hospedesRestantes = numeroDeHospedesDoGrupo;
//            for (Quarto quarto : quartos) {
//                synchronized (quarto) {
//                    if (quarto.estaDisponivel()) {
//                        int capacidadeDisponivel = quarto.getCapacidadeDoQuarto() - quarto.getHospedesAlocadosNoQuarto();
//                        if (hospedesRestantes >= capacidadeDisponivel) {
//                            quarto.adicionarHospedes(capacidadeDisponivel);
//                            hospedesRestantes -= capacidadeDisponivel;
//                        } else {
//                            quarto.adicionarHospedes(hospedesRestantes);
//                            break; // Não precisamos mais alocar em outros quartos
//                        }
//                    }
//                }
//            }
//        } else {
//            // Não há quartos disponíveis suficientes para acomodar o grupo
//            System.out.println("Não há quartos disponíveis suficientes para acomodar o grupo.");
//        }
//    }



}
