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
                // Procura por um quarto disponível
                Quarto quartoDisponivel = procurarQuartoDisponivel();
                if (quartoDisponivel != null) {
                    // Se encontrou um quarto disponível, tenta alocar os grupos nele
                    alocarGruposNoQuarto(quartoDisponivel);
                    // Uma vez que todos os grupos foram alocados, a tarefa do recepcionista está completa
                    break;
                } else {
                    // Se não há quarto disponível, espera por um segundo antes de tentar novamente
                    System.out.println("Recepcionista " + nome + " não tem quarto disponível no momento; (esperando por quartos disponíveis).");
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Tenta alocar os grupos em um quarto disponível
    public void alocarGruposNoQuarto(Quarto quarto) {
        synchronized (quarto) {
            for (GrupoHospedes grupo : grupos) {
                if (grupo.getTamanho() <= 4) {
                    // Se o grupo cabe em um quarto, aloca-o diretamente
                    alocarGrupo(quarto, grupo);
                } else {
                    // Se o grupo for maior que a capacidade do quarto, divide-o em grupos menores
                    dividirGrupoEAlugar(quarto, grupo);
                }
            }
        }
    }

    // Tenta alocar um grupo em um quarto
    public void alocarGrupo(Quarto quarto, GrupoHospedes grupo) {
        if (quarto.isDisponivel()) {


            // Se o quarto está disponível, aloca o grupo nele
            System.out.println("Recepcionista- " + nome + " alocou o " + grupo.getNome()  +
                    " para o quarto " + quarto.getNumeroQuarto()  + "; (Situação do quarto "+quarto.getNumeroQuarto()+": está com " + grupo.getTamanho() + " membros dentro do quarto).");
            quarto.adicionarGrupoDeHospedes(grupo); // o grupo é adicionado a uma lista de GrupoHospede, essa lista contem todos os grupos; em seguida associamos este grupo a um quarto

            temporizarEstadia(grupo, quarto);
        } else {
            // Se o quarto não está disponível, tenta alocar o grupo em outro quarto
            tentarAlocarGrupoEmOutroQuarto(quarto, grupo);

        }
    }

    // Divide um grupo grande em grupos menores e tenta alocá-los
    private void dividirGrupoEAlugar(Quarto quarto, GrupoHospedes grupo) {
        while (grupo.getTamanho() > 0) {
            GrupoHospedes novoGrupo = new GrupoHospedes(grupo.getNome());
            for (int i = 0; i < 4 && grupo.getTamanho() > 0; i++) {
                novoGrupo.adicionarHospedeDentroDoGrupo(grupo.getHospedes().get(0));
                grupo.getHospedes().remove(0);
            }
            tentarAlocarGrupoEmOutroQuarto(quarto, novoGrupo);
        }
    }

    // Tenta alocar um grupo em outro quarto disponível
    private void tentarAlocarGrupoEmOutroQuarto(Quarto quarto, GrupoHospedes grupo) {
        Quarto novoQuarto = procurarQuartoDisponivel();
        if (novoQuarto != null) {
            alocarGrupo(novoQuarto, grupo);

            temporizarEstadia(grupo, quarto);
        } else {
            // Se não há outro quarto disponível, exibe uma mensagem
            System.out.println("Não há quartos suficientes para o " + grupo.getNome());
        }
    }

    // Procura por um quarto disponível na lista de quartos
    private synchronized Quarto procurarQuartoDisponivel() {
        for (Quarto quarto : quartosDisponiveis) {
            if (quarto.isDisponivel()) {
                return quarto;
            }
        }
        return null;
    }



    private void temporizarEstadia(GrupoHospedes grupo, Quarto quarto) {
        // é criado um novo objeto thread para a thread recepcionista não ficar travada
        // até o fim da estadia do grupo... assim a recepcionista consegue atender outros grupos
        new Thread(() -> {
            try {
                Thread.sleep(2000); // Tempo de estadia de 2 segundos
                checkOut(grupo, quarto);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void checkOut(GrupoHospedes grupo, Quarto quarto) {

        synchronized (quarto) {
            if (quarto.getGrupos().contains(grupo)) {
                quarto.removerGrupoDeHospedes(grupo);
                quarto.setDisponivel(true);
                System.out.println("Recepcionista- " + nome + " realizou o check-out do " + grupo.getNome() + " para o quarto " + quarto.getNumeroQuarto());
                System.out.println("Quarto " + quarto.getNumeroQuarto() + " agora está disponível para novos hóspedes.");
//                System.out.println("Estado atual do quarto " + quarto.getNumeroQuarto() + ":"+quarto.isDisponivel()+"\n--------------------------------------------------------");
            }
        }
    }

    public List<Quarto> getQuartosDisponiveis() {
        return quartosDisponiveis;
    }
}
