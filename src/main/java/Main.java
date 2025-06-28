public class Main {
    public static void main(String[] args) {
        System.out.println("--- DEMONSTRAÇÃO DO PADRÃO MEMENTO ---");
        Editor editor = new Editor("Este é o texto inicial.");
        Caretaker historico = new Caretaker();

        // Salva o estado inicial
        historico.adicionar(editor.salvar());
        System.out.println("Estado atual: " + editor);

        // Altera o conteúdo e salva o novo estado
        editor.setConteudo("Agora o texto tem uma segunda versão.");
        historico.adicionar(editor.salvar());
        System.out.println("Estado atual: " + editor);

        // Altera o conteúdo novamente, mas não salva
        editor.setConteudo("Uma terceira versão, mas esta não será salva.");
        System.out.println("Estado atual: " + editor);

        // Restaura para a primeira versão (índice 0)
        System.out.println("\n--- Restaurando para a primeira versão do texto ---");
        editor.restaurar(historico.get(0));
        System.out.println("Estado atual restaurado: " + editor);

        System.out.println("\n--- DEMONSTRAÇÃO DO PADRÃO PROTOTYPE ---");
        Editor editorOriginal = new Editor("Conteúdo original para clonagem.");
        System.out.println("Editor Original: " + editorOriginal);

        // Clona o editor original
        Editor editorClonado = editorOriginal.clone();
        System.out.println("Editor Clonado (inicialmente igual): " + editorClonado);

        // Modifica o editor clonado. O original não deve ser afetado.
        editorClonado.setConteudo("Conteúdo modificado do editor clonado.");
        System.out.println("Editor Clonado (após modificação): " + editorClonado);
        System.out.println("Editor Original (após modificação do clonado): " + editorOriginal); // Permanece inalterado

        // Cria outro clone para mostrar independência
        Editor outroEditorClonado = editorOriginal.clone();
        outroEditorClonado.setConteudo("Este é outro clone com texto diferente.");
        System.out.println("Outro Editor Clonado: " + outroEditorClonado);
        System.out.println("Editor Original (confirmação final): " + editorOriginal);
    }
}