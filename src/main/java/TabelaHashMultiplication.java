public class TabelaHashMultiplication implements TabelaHashProjeto{

    private Integer[] tabela;
    private int tamanho;
    private int colisoes;
    private static final Integer DELETED = Integer.MIN_VALUE;
    private double fatorDeCarga;

    public static final int CAPACIDADE_DEFAULT = 1500;

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
        return chave % this.tabela.length;
    }

    private int funcaoHashMultiplicacao(Integer chave){
        double a = 0.6180339887; // utilizando a constante sugerida por Knuth
        double hash = ((chave * a) % 1) * this.tabela.length;
        return (int)hash;
    }

    @Override
    public void add(Integer chave) {

    }

    @Override
    public Integer remove(Integer chave) {
        return 0;
    }

    @Override
    public void resize() {

    }

    @Override
    public boolean contains(Integer chave) {
        return false;
    }

    @Override
    public int size() {
        return this.tamanho;
    }
}
