package funcionalidades.DHospedeTentaAlugarQuarto;

import java.util.ArrayList;
import java.util.List;

public class GrupoHospedes {
    private String nome;
    private List<Hospede> hospedes;
    private Camareira camareira;

    public GrupoHospedes(String nome) {
        this.nome = nome;
        this.hospedes = new ArrayList<>();
    }

    // ---
    // INSERE OS HOSPEDES QUE ESTÃO JUNTOS(grupo/familia) DENTRO DE UMA LISTA{seus elementos são hospedes individuais}
    public void adicionarHospedeDentroDoGrupo(Hospede hospede) { // adiciona hospedes a um grupo
        hospedes.add(hospede);
    }
    // ---


    // ---
    // ---
    // Método para simular o passeio do grupo
    public void simularPasseio(Quarto quarto) {
        simulaSaida(quarto);
        // Realiza outras atividades durante o passeio, ...faltou... parte em que camareira devere entrar
        simulaEntrada(quarto);
    }

    // Simula a saída do grupo do quarto para passear
    private void simulaSaida(Quarto quarto) {
        // Devolve a chave na recepção
        System.out.print("O "+this.getNome()+" foi passear; (");
        quarto.devolverChaveNaRecepcao();
        System.out.println(nome+" DEIXOU A CHAVE do quarto na recepção).");
    }

    // Simular o retorno do grupo do passeio para o quarto
    private void simulaEntrada(Quarto quarto) {
        // Pega a chave na recepção
        System.out.print(this.getNome()+" voltou do passeio; (");
        quarto.pegarChaveDaRecepcao();
        System.out.println(nome+" PEGOU A CHAVE do quarto na recepção).");
    }

    // ---
    // ---



    // getters
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
