public class Hospede extends Thread {
    private String nome;


    public Hospede(String nome) {
        this.nome = nome;
    }

    @Override
    public void run() {
        System.out.println("Hóspede " + nome + " chegou ao hotel.");
        tentarAlugarQuarto();
    }

    // Método para tentar alugar um quarto disponível para o hóspede
    public void tentarAlugarQuarto() {
        try {
            Quarto quartoDisponivel = procurarQuartoDisponivel();
            if (quartoDisponivel != null) {
                alugarQuarto(quartoDisponivel);
                System.out.println("Hóspede " + nome + " alugou um quarto.");
                return; // Retorna se o quarto foi alocado com sucesso
            } else {
                // Se não há quarto disponível, espera por um segundo antes de tentar novamente
                Thread.sleep(2000);
                // pós 2 segundo tenta de novo alugar um quarto disponível
		if (quartoDisponivel != null) {
			alugarQuarto(quartoDisponivel);
			System.out.println("Hóspede " + nome + " alugou um quarto.");
			return;
		} else {
			System.out.println("(Reclamacao)-Hospede " + nome + " nao conseguiu alugar um quarto e esta indo embora.");
		}
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Hóspede " + nome + " não conseguiu alugar um quarto e está indo embora.");
    }

    // Método para alugar um quarto disponível
    private void alugarQuarto(Quarto quarto) {
//        quarto.adicionarGrupoDeHospedes(this);
    }

    // Método para procurar por um quarto disponível na lista de quartos
    private Quarto procurarQuartoDisponivel() {
        return null;
    }
    public String getNome() {
        return nome;
    }
}
