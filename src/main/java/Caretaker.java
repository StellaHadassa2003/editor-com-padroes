import java.util.ArrayList;
import java.util.List;

public class Caretaker {
    private final List<Memento> estadosSalvos = new ArrayList<>();

    public void adicionar(Memento memento) {
        estadosSalvos.add(memento);
        System.out.println("Caretaker: Estado adicionado ao histórico.");
    }

    public Memento get(int index) {
        if (index >= 0 && index < estadosSalvos.size()) {
            System.out.println("Caretaker: Recuperando estado do histórico na posição " + index + ".");
            return estadosSalvos.get(index);
        }
        System.out.println("Caretaker: Índice inválido para o histórico.");
        return null;
    }

    public int size() {
        return estadosSalvos.size();
    }
}