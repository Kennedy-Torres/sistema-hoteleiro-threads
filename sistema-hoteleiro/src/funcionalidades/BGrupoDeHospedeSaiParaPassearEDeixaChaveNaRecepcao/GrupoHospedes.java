package funcionalidades.BGrupoDeHospedeSaiParaPassearEDeixaChaveNaRecepcao;

import java.util.ArrayList;
import java.util.List;

public class GrupoHospedes {
    private String nome;
    private List<Hospede> hospedes;

    public GrupoHospedes(String nome) {
        this.nome = nome;
        this.hospedes = new ArrayList<>();
    }

    public void adicionarHospedeDentroDoGrupo(Hospede hospede) {
        hospedes.add(hospede);
    }

    // Simula a saída do grupo para passear
    public void simularSaidaParaPassear(Quarto quarto) {
        // Remove todos os hóspedes do quarto
        quarto.getHospedes().clear();
        quarto.devolverChaveNaRecepcao(); // Devolve a chave na recepção
    }

    public void retornarParaQuarto(Quarto quarto) {
        // Adicione a lógica para retornar ao quarto aqui !!!!! só pode voltar quando a camareira tiver terminado de limpar

        for(Hospede hospede: hospedes) { // iterando sobre a lista de hóspedes do grupo e adicionando cada um deles individualmente ao quarto
            quarto.adicionarHospede(hospede);
        }
        System.out.println("Grupo do quarto " + quarto.getNumeroQuarto()+" voltou do passeio.");

        // Verifica a quantidade de hóspedes no quarto após o retorno
        System.out.println("Número de hóspedes no quarto após o retorno: " + quarto.getHospedes().size());

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
