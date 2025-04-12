public interface TabelaHashProjeto {
    int hash(Integer chave);

    void add(Integer chave);

    void resize();

    int size();

    int getColisoes();
}
