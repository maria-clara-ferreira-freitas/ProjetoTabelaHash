import java.util.*;

public class TabelaHashMidSquare implements TabelaHashProjeto{

    private ArrayList<Integer>[] table;
    private int tamanho;
    private int numeroDeColisoes;
    private int count;
    private double fatorDeCarga;
    private static final double FATOR_DE_CARGA_DEFAULT = 0.85;

    public TabelaHashMidSquare() {
        this.tamanho = 3000;
        this.table = new ArrayList[tamanho];
        for (int i = 0; i < tamanho; i++) {
            table[i] = new ArrayList<>();
        }
        this.numeroDeColisoes = 0;
        this.count = 0;
        this.fatorDeCarga = FATOR_DE_CARGA_DEFAULT;

    }

    @Override
    public int hash(Integer chave) {
        return hashingMidSquare(chave, tamanho);
    }

    @Override
    public void add(Integer chave) {
        if ((double) this.count / this.tamanho > this.fatorDeCarga) {
            resize();
        }
        int hash = hash(chave);
        if (!this.table[hash].contains(chave)) {
            if(!table[hash].isEmpty()){
                numeroDeColisoes++;
            }
            table[hash].add(chave);
            count++;
        }
    }

    @Override
    public void resize() {
        int novoTamanho = findNextPrime(this.tamanho * 2);
        ArrayList<Integer>[] novaTabela = new ArrayList[novoTamanho];
        for (int i = 0; i < novoTamanho; i++) {
            novaTabela[i] = new ArrayList<>();
        }
        for (ArrayList<Integer> bucket : this.table) {
            for (Integer chave : bucket) {
                int novoHash = hashingMidSquare(chave, novoTamanho);
                novaTabela[novoHash].add(chave);
            }
        }
        this.table = novaTabela;
        this.tamanho = novoTamanho;
    }

    @Override
    public int size() {
        return this.tamanho;
    }

    @Override
    public int getColisoes() {
        return this.numeroDeColisoes;
    }

    private int findNextPrime(int num) {
        while (!isPrime(num)) {
            num++;
        }
        return num;
    }
    private boolean isPrime(int num) {
        if (num <= 1) return false;
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) return false;
        }
        return true;
    }

    public static int contarDigitos(long chave) {
        int contagem = 0;
        while (chave != 0) {
            chave /= 10;
            ++contagem;
        }
        return contagem;
    }

    // Função para pegar o N-ésimo dígito de um número
    public static int pegarDigitoN(long numero, int pos) {
        int digito = 0;
        int tamanho = contarDigitos(numero);
        int fração = (int) (numero / Math.pow(10, (tamanho - pos))); // Pegando a fração que corresponde ao dígito na posição
        digito = fração % 10; // Pegando o dígito da posição
        return digito;
    }

    // Função para pegar um intervalo de dígitos de um número
    public static int pegarIntervaloDeDigitos(long numero, int posInicial, int quantidade) {
        int numeroResultado = 0;
        int digito;
        int tamanho = contarDigitos(numero);
        // Verifica se a quantidade de dígitos que queremos pegar está dentro do número
        if ((posInicial + quantidade) - 1 <= tamanho) {
            for (int i = 0; i < quantidade; i++) {
                // Pegando o dígito da posição
                digito = pegarDigitoN(numero, posInicial);
                posInicial++;
                // Criando o número com os dígitos selecionados
                numeroResultado = numeroResultado * 10 + digito;
            }
        }
        return numeroResultado;
    }

    private int hashingMidSquare(int chave, int tamanho) {
        long squared = (long) chave * (long) chave;
        int totalDigitos = contarDigitos(squared);

        // Exemplo: queremos pegar 4 dígitos centrais
        int quantidade = 4;
        int posInicial = (totalDigitos - quantidade) / 2 + 1;

        int digitosDoMeio = pegarIntervaloDeDigitos(squared, posInicial, quantidade);
        return digitosDoMeio % tamanho;
    }

}
