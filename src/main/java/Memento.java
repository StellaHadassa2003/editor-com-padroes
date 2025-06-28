// Memento.java
// Este arquivo deve estar na mesma pasta que Main.java, Editor.java, Caretaker.java

import java.io.Serializable;

public class Memento implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String conteudo;

    public Memento(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getConteudo() {
        return conteudo;
    }
}