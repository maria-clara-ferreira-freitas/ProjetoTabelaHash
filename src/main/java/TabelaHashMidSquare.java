import java.io.*;
import java.util.*;

public class TabelaHashMidSquare implements TabelaHashProjeto{

    private ArrayList<Integer>[] table;
    private int tamanho;
    private int numeroDeColisoes;
    private int count;
    private double fatorDeCarga;
    private static final double FATOR_DE_CARGA_DEFAULT = 0.85;
    private BufferedWriter writer;

    public TabelaHashMidSquare(String arquivoSaida) {
        this.tamanho = tamanho;
        this.table = new ArrayList[tamanho];
        this.numeroDeColisoes = 0;
        this.count = 0;
        this.fatorDeCarga = FATOR_DE_CARGA_DEFAULT;
        try {
            this.writer = new BufferedWriter(new FileWriter(arquivoSaida, true)); // true para append
        } catch (IOException e) {
            System.out.println("Erro ao abrir arquivo de saída: " + e.getMessage());
        }        for (int i = 0; i < this.tamanho; i++) {
            this.table[i] = new ArrayList<>();
        }
    }

    @Override
    public int hash(Integer chave) {
        return Math.abs((chave * 31) % this.tamanho);
    }

    @Override
    public void add(Integer chave) {
        if ((double) this.count / this.tamanho > this.fatorDeCarga) {
            resize();
        }
        int hash = hash(chave);
        if (!this.table[hash].contains(chave)) { // Evita inserir chave duplicada
            boolean colisao = !this.table[hash].isEmpty();
            this.table[hash].add(chave);
            try {
                if (colisao) {
                    numeroDeColisoes++;
                    writeToFile("Chave " + chave + " adicionada ao hash " + (hash % this.tamanho) + " - Colisão!");
                } else {
                    writeToFile("Chave " + chave + " adicionada ao hash " + (hash % this.tamanho));
                }
            } catch (IOException e) {
                System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
            }
            this.count++;
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
            if (bucket != null) {
                for (int chave : bucket) {
                    int novoHash = Math.abs((chave * 31) % novoTamanho);
                    novaTabela[novoHash].add(chave);
                }
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

    public void writeToFile(String message) throws IOException {
        writer.write(message);
        writer.newLine();
    }
    public void closeWriter() {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            System.out.println("Erro ao fechar o arquivo: " + e.getMessage());
        }
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
    // Função de hashing utilizando o método do quadrado do meio
    public static int hashingMidSquare(int chave, int tamanho) {
        // Método da média quadrada para gerar o hash
        int hash = chave * chave; // Calculando o quadrado da chave
        // Garantir que o valor do hash esteja dentro do intervalo da tabela
        int hashAjustado = Math.abs(hash) % tamanho; // Ajustando para garantir que está dentro dos limites da tabela
        // Verificando se o hash calculado é válido
        System.out.println("Chave: " + chave + " -> Hash calculado: " + hash + " -> Hash ajustado: " + hashAjustado);
        return hashAjustado;
    }
}
