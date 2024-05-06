package funcionalidades.CRecepcionistaFazCheckoutDeHospedes.tentativa02ComPristsDeTestes;

import java.util.ArrayList;
import java.util.List;

public class GrupoHospedes {
    private String nome;
    private List<Hospede> hospedes;

    public GrupoHospedes(String nome) {
        this.nome = nome;
        this.hospedes = new ArrayList<>();
    }

    public void adicionarHospedeDentroDoGrupo(Hospede hospede) { // adiciona hospedes a um grupo
        hospedes.add(hospede);
    }

    // Simula a saída do grupo para passear
    // Método para simular o passeio do grupo
    public void simularPasseio(Quarto quarto) {
        simulaSaida(quarto);
        // Realiza outras atividades durante o passeio, se necessário
        simulaEntrada(quarto);
    }

    // Método para simular a saída do grupo para o passeio
    private void simulaSaida(Quarto quarto) {

        // Devolve a chave na recepção
        quarto.devolverChaveNaRecepcao(); // Simula a saída do grupo do quarto para passear
//        quarto.simularPasseio(); // Simula a saída do grupo do quarto para passear ... dps retirar o metodo na classe quarto
    }

    // Método para simular o retorno do grupo do passeio
    private void simulaEntrada(Quarto quarto) {
        // Pega a chave na recepção
        System.out.print("O grupo do quarto"+quarto.getNumeroQuarto()+" voltou do passeio; (");
        quarto.pegarChaveDaRecepcao();
//        quarto.simularRetornoDoPasseio(); ... dps retirar o metodo na classe quarto
    }

    // Método para pegar a chave na recepção ao retornar do passeio
    public void pegarChaveNaRecepcao(Quarto quarto) {
        if (quarto != null) {
            quarto.pegarChaveDaRecepcao(); // Pega a chave da recepção do mesmo quarto em que estava hospedado
        } else {
            System.out.println("O grupo não estava hospedado em nenhum quarto antes de sair para passear.");
        }
    }


    // Verifica se a chave está na recepção após a saída do grupo para passear
    public boolean verificarChaveNaRecepcao(Quarto quarto) {
        return quarto.isChaveNaRecepcao();
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
