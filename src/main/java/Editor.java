public class Editor implements Cloneable {
    private String conteudo;

    public Editor(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
        System.out.println("Editor: Conteúdo atualizado para: \"" + this.conteudo + "\"");
    }

    // Método para salvar o estado atual (cria um Memento)
    public Memento salvar() {
        System.out.println("Editor: Salvando estado atual...");
        return new Memento(conteudo);
    }

    // Método para restaurar o estado a partir de um Memento
    public void restaurar(Memento memento) {
        this.conteudo = memento.getConteudo();
        System.out.println("Editor: Estado restaurado para: \"" + this.conteudo + "\"");
    }

    // Implementação do padrão Prototype
    @Override
    public Editor clone() {
        try {
            // Realiza uma cópia superficial. Para atributos mutáveis mais complexos
            // que não sejam Strings, uma cópia profunda seria necessária.
            Editor clone = (Editor) super.clone();
            // Exemplo de deep copy se 'conteudo' fosse um objeto mutável:
            // clone.conteudo = new AlgumObjetoMutavel(this.conteudo.getPropriedade());
            return clone;
        } catch (CloneNotSupportedException e) {
            // Isso não deveria acontecer, pois a classe implementa Cloneable
            throw new AssertionError("Erro ao clonar Editor", e);
        }
    }

    @Override
    public String toString() {
        return "Editor [conteudo=\"" + conteudo + "\"]";
    }
}