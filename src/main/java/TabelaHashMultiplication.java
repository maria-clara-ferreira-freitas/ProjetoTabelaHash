import java.util.ArrayList;
import java.util.List;

public class TabelaHashMultiplication implements TabelaHashProjeto{

    private Integer[] tabela;
    private int tamanho;
    private int colisoes;
    private double fatorDeCarga;

    private static final Integer DELETED = Integer.MIN_VALUE;
    public static final int CAPACIDADE_DEFAULT = 3000;
    public static final double FATOR_DE_CARGA_DEFAULT = 0.85;

    public TabelaHashMultiplication(){
        this(CAPACIDADE_DEFAULT, FATOR_DE_CARGA_DEFAULT);
    }

    public TabelaHashMultiplication(int capacidade, double fatorDeCarga) {
        this.tabela = new Integer[capacidade];
        this.fatorDeCarga = fatorDeCarga;
        this.tamanho = 0;
        this.colisoes = 0;
    }

    @Override
    public int hash(Integer chave) {
        double a = 0.6180339887; // utilizando a constante sugerida por Knuth
        double hash = ((chave * a) % 1) * this.tabela.length;
        return (int)hash;
    }

    @Override
    public void add(Integer chave) {
        if((this.tamanho / this.tabela.length) >= fatorDeCarga){
            resize();

        }
        int sondagem = 0;
        int hash;

        while (sondagem < tabela.length) {

            hash = (hash(chave) + sondagem) % tabela.length;

            if (tabela[hash] == null || tabela[hash] == chave || tabela[hash] == DELETED) {
                tabela[hash] = chave;
                this.tamanho++;
                return;
            }

            sondagem += 1;
            colisoes += 1;

        }

    }

    @Override
    public Integer remove(Integer chave) {
        return 0;
    }

    @Override
    public void resize() {
        int novoTamanho = this.tamanho * 2;
        Integer[] novaTabela = new Integer[novoTamanho];

        for (int i = 0; i < this.tabela.length; i++) {
            if (this.tabela[i] != null && this.tabela[i] != DELETED) {
                int sondagem = 0;
                int rehash = hash(this.tabela[i]) % novoTamanho;

                // Encontra a posição disponível na nova tabela
                while (novaTabela[rehash] != null) {
                    sondagem++;
                    rehash = (rehash + sondagem) % novoTamanho;
                }

                novaTabela[rehash] = this.tabela[i];
            }
        }

        // Atualiza a tabela com a nova versão
        this.tabela = novaTabela;
    }


    @Override
    public boolean contains(Integer chave) {
        int sondagem = 0;
        int hash;

        while(sondagem < tabela.length){

            hash = (hash(chave) + sondagem) % tabela.length;

            if(tabela[hash] == null) return false;
            if(tabela[hash] == chave) return true;

            sondagem++;
        }
        return false;
    }

    @Override
    public int size() {
        return this.tamanho;
    }

    public int getColisoes(){
        return this.colisoes;
    }
}
