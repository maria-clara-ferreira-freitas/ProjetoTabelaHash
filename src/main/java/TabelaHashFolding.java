import java.io.*;
import java.util.*;

public class TabelaHashFolding implements TabelaHashProjeto {
    private List<Integer> table;
    private int capacidade;
    private int colisoes;

    public TabelaHashFolding(int capacidadeInicial) {
        this.capacidade = capacidadeInicial;
        this.table = new ArrayList<>(capacidadeInicial);
        for (int i = 0; i < capacidadeInicial; i++) {
            table.add(null);
        }
        this.colisoes = 0;
    }

    @Override
    public int hash(Integer chave) {
        String keyString = chave.toString();
        int sum = 0;

        // Divide a chave em partes e soma os valores
        for (int i = 0; i < keyString.length(); i += 2) {
            int part = Integer.parseInt(keyString.substring(i, Math.min(i + 2, keyString.length())));
            sum += part;
        }

        return sum % capacidade;
    }

    @Override
    public void add(Integer chave) {
        int index = hash(chave);

        if (table.get(index) != null) {
            colisoes++; // Incrementa as colisões se o índice já estiver ocupado
        }

        // Trata colisões (neste caso, substitui a chave antiga pela nova)
        table.set(index, chave);
    }

    @Override
    public void resize() {
        capacidade *= 2; // Dobra a capacidade
        List<Integer> oldTable = table;
        table = new ArrayList<>(capacidade);

        // Inicializa a nova tabela com valores nulos
        for (int i = 0; i < capacidade; i++) {
            table.add(null);
        }

        colisoes = 0; // Reinicia o contador de colisões

        // Re-hash das chaves antigas
        for (Integer chave : oldTable) {
            if (chave != null) {
                add(chave);
            }
        }
    }

    @Override
    public int size() {
        return capacidade;
    }

    @Override
    public int getColisoes() {
        return colisoes;
    }
