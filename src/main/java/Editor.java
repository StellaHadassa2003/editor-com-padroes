// Editor.java
// Este arquivo deve estar na mesma pasta que Main.java, Memento.java, Caretaker.java

import java.io.Serializable;

public class Editor implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L;
    private String conteudo;

    public Editor(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Memento salvar() {
        return new Memento(conteudo);
    }

    public void restaurar(Memento memento) {
        this.conteudo = memento.getConteudo();
    }

    @Override
    public Editor clone() {
        try {
            Editor clone = (Editor) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Erro ao clonar Editor", e);
        }
    }

    @Override
    public String toString() {
        return conteudo;
    }
}