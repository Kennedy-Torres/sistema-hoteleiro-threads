package funcionalidades.QuartoVagoParaHospede.AtribuiQuartoACadaGrupo.AtribuiApenasQuartosVagosENotificaQuandoNaoTemQuartosSuficientes;

import java.util.List;

public class Recepcionista extends Thread {
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
                                        novoGrupo.adicionarHospedeDentroDoGrupo(grupo.getHospedes().get(0));
                                        grupo.getHospedes().remove(0);
                                    }
                                    alocarGrupo(procurarQuartoDisponivel(), novoGrupo);
                                }
                            }
                        }
                        break; // Uma vez que todos os grupos foram alocados, a tarefa do recepcionista está completa
                        // quebra o laço while
                    }
                    // !!!!!!!!!!!! até então o código nunca chega até o bloco else VERIFICAR DEPOIS !!!!!!!!!
                } else {
                    System.out.println("Recepcionista " + nome + " não tem quarto disponível no momento; (esperando por quartos disponíveis).");
                    Thread.sleep(1000); // Espera um segundo antes de tentar novamente
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /* cada thread de Recepcionista procura por quartos disponíveis
    *  e aloca grupos de hóspedes
    */
    private void alocarGrupo(Quarto quarto, GrupoHospedes grupo) {
        if (quarto.isDisponivel()) {
            System.out.println("Recepcionista- " + nome + " alocou o " + grupo.getNome()  +
                    " para o quarto " + quarto.getNumeroQuarto()  + "; (Situação do quarto "+quarto.getNumeroQuarto()+": está com " + grupo.getTamanho() + " membros dentro do quarto).");
            for (Hospede hospede : grupo.getHospedes()) {
                quarto.adicionarHospede(hospede);
            }
        } else {
            // Se o quarto não estiver disponível, tenta alocar o grupo em outro quarto
            Quarto novoQuarto = procurarQuartoDisponivel();
            if (novoQuarto != null) {
                alocarGrupo(novoQuarto, grupo);
            } else {
                // por fim se não achar nenhum quarto disponivel manda uma mensagem
                System.out.println("Não há quartos disponíveis para o " + grupo.getNome());
            }
        }
    }

    private Quarto procurarQuartoDisponivel() {
        for (Quarto quarto : quartosDisponiveis) {
            if (quarto.isDisponivel() /*&& !quartoPossuiHospedes(quarto)*/) { // !!!
                return quarto;
            }
        }
        return null;
    }

    private boolean quartoPossuiHospedes(Quarto quarto) {
        return !quarto.getHospedes().isEmpty();
    }
}
