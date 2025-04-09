public interface TabelaHashProjeto {
    public int hash(Integer chave);

    public void add(Integer chave);

    //public Integer remove(Integer chave);

    void resize();

    //public boolean contains(Integer chave);

    public int size();

    public int getColisoes();
}
