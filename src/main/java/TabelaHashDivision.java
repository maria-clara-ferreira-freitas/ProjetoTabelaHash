import java.util.LinkedList;
import java.util.List;

public class TabelaHashDivision implements TabelaHashProjeto {
    private List<LinkedList<Integer>> tabela;
    private int capacidade;
    private int quantidade;
    private int colisoes;
    private static final int CAPACIDADE_DEFAULT = 3000;
    private static final double FATOR_DE_CARGA_DEFAULT = 0.85;


    public TabelaHashDivision() {
        this(CAPACIDADE_DEFAULT);
    }
    
    public TabelaHashDivision(int capacidade) {
        this.capacidade = capacidade;
        this.tabela = new LinkedList<>();
        this.colisoes = 0;
        this.quantidade = 0;
        for (int i = 0; i < capacidade; i++) {
            tabela.add(new LinkedList<>());
        }
    }

    public int hash(Integer chave) {
        return chave % capacidade;
    }

    public static boolean numeroEPrimo(int numero) {
        if (numero <= 1) return false;
        if (numero == 2 || numero == 3) return true;
        if (numero % 2 == 0) return false;
        for (int i = 3; i * i <= numero; i += 2) {
            if (numero % i == 0) return false;
        }
        return true;
    }

    public static int getProximoPrimo(int numero) {
        while (!numeroEPrimo(numero)) {
            numero++;
        }
        return numero;
    }


    public void add(Integer chave) {
        if ((double) quantidade / capacidade > FATOR_DE_CARGA_DEFAULT) {
            resize();
        }
        int index = hash(chave);
        if (tabela.get(index) == null) {
            tabela.set(index, new LinkedList<>());  
        }
        LinkedList<Integer> listas = tabela.get(index);
        if (!listas.contains(chave)) {
            if (!listas.isEmpty()){
                colisoes++;
            }
            listas.add(chave);
            quantidade++;
        }
    }

    public void resize() {
        int novoSize = getProximoPrimo(capacidade * 2);
        List<LinkedList<Integer>> novaTabela = new LinkedList<>();
        for (int i = 0; i < novoSize; i++) {
            novaTabela.add(new LinkedList<>());
        }
        for (int i = 0; i < capacidade; i++) {
            LinkedList<Integer> lista = tabela.get(i);
            for (Integer chave : lista) {
                int novoI = chave % novoSize;
                novaTabela.get(novoI).add(chave);
            }
        }
        this.tabela = novaTabela;
        this.capacidade = novoSize;
    }

    public int size() {
        int contador = 0;
        for (LinkedList<Integer> lista : tabela) {
        	contador += lista.size();
        }
        return contador;
    }

    public int getColisoes() {
        return this.colisoes;
    }
}
