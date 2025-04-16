import java.util.*;

public class TabelaHashFolding implements TabelaHashProjeto {
    private List<Integer> table;
    private int capacidade;
    private int quantidade;
    private int colisoes;
    private static final double FATOR_DE_CARGA_DEFAULT = 0.85;

    public TabelaHashFolding() {
        this.capacidade = 3000;
        this.table = new ArrayList<>(capacidade);
        for (int i = 0; i < capacidade; i++) {
            table.add(null);
        }
        this.colisoes = 0;
        this.quantidade = 0;
    }

    @Override
    public int hash(Integer chave) {
        String keyString = chave.toString();
        int sum = 0;

        // Divide a chave em partes e soma os valores
        for (int i = 0; i < keyString.length(); i += 2) {
            int part = Integer.parseInt(keyString.substring(i, Math.min(i + 2, keyString.length())));
            sum = (sum * 31 + part) % capacidade;

        }

        return sum;
    }

    @Override
    public void add(Integer chave) {
        if((double) quantidade / capacidade > FATOR_DE_CARGA_DEFAULT){
            resize();
        }

        int index = hash(chave);

        if (table.get(index) != null && !table.get(index).equals(chave)) {
            colisoes++; // Incrementa as colisões se o índice já estiver ocupado
        }

        table.set(index, chave);
        quantidade++;
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

        // Re-hash das chaves antigas
        for (Integer chave : oldTable) {
            if (chave != null) {
                int index = hash(chave);
                table.set(index, chave);
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
}
