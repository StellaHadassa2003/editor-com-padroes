// Caretaker.java

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// Adicione estas duas linhas de importação:
import java.io.IOException;
import java.io.ObjectInputStream;

public class Caretaker implements Serializable {
    private static final long serialVersionUID = 1L;

    private final List<Memento> todosEstadosSalvos = new ArrayList<>();
    private transient Stack<Memento> undoStack;

    public Caretaker() {
        this.undoStack = new Stack<>();
    }

    // Método de callback para ser chamado após a desserialização
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject(); // Lê os campos não transientes
        this.undoStack = new Stack<>(); // Inicializa a Stack após a desserialização
        // Preenche a Stack com os estados carregados da lista
        for (Memento memento : todosEstadosSalvos) {
            this.undoStack.push(memento);
        }
    }

    public void adicionar(Memento memento) {
        todosEstadosSalvos.add(memento);
        undoStack.push(memento); // Adiciona também à stack para undo
    }

    public Memento get(int index) {
        if (index >= 0 && index < todosEstadosSalvos.size()) {
            return todosEstadosSalvos.get(index);
        }
        return null;
    }

    public int size() {
        return todosEstadosSalvos.size();
    }

    public Memento popUndo() {
        if (undoStack.size() > 1) {
            return undoStack.pop();
        }
        return null;
    }

    public Memento peekUndo() {
        if (!undoStack.isEmpty()) {
            return undoStack.peek();
        }
        return null;
    }

    public boolean isUndoStackEmpty() {
        return undoStack.isEmpty();
    }

    public int getUndoStackSize() {
        return undoStack.size();
    }

    public void clearUndoStack() {
        undoStack.clear();
    }
}