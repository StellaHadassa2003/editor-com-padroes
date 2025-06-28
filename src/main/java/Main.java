// Main.java
// Este é o arquivo que será executado pelo Replit, pois ele contém o método main.

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
// Não precisamos importar Stack aqui, pois Caretaker a gerencia internamente

public class Main extends JFrame { // Renomeado de EditorGUI para Main para ser o ponto de entrada do Replit

    private JTextField campoTexto;
    private JTextArea areaTextoCopiado;
    private JButton botaoGravar;
    private JButton botaoDesfazer;
    private JButton botaoClonar;

    private Editor editor;
    private Caretaker historico;

    private static final String ARQUIVO_HISTORICO = "historico_editor.ser"; // Nome do arquivo para persistência

    public Main() { // Construtor renomeado para Main
        super("Editor Simples com Padrões");

        editor = new Editor("");
        historico = new Caretaker();

        carregarHistoricoDoDisco();

        setSize(550, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Evento de fechamento da janela para salvar o histórico
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                salvarHistoricoNoDisco();
                // System.exit(0); // Comentei para que o Replit finalize de forma mais limpa se precisar
            }
        });

        // Componentes da Interface
        campoTexto = new JTextField(30);
        areaTextoCopiado = new JTextArea(10, 45);
        areaTextoCopiado.setEditable(false);
        areaTextoCopiado.setLineWrap(true);
        areaTextoCopiado.setWrapStyleWord(true);

        botaoGravar = new JButton("Gravar e Limpar");
        botaoDesfazer = new JButton("Desfazer");
        botaoClonar = new JButton("Clonar Editor");

        // Layout (FlowLayout simples para demonstração)
        setLayout(new FlowLayout());

        add(new JLabel("Digite seu texto:"));
        add(campoTexto);
        add(botaoGravar);
        add(botaoDesfazer);
        add(botaoClonar);
        add(new JScrollPane(areaTextoCopiado));

        // Adiciona listeners aos botões
        adicionarListeners();

        // Se houver histórico salvo, restaura o último estado no campoTexto
        if (historico.getUndoStackSize() > 0) {
            editor.restaurar(historico.peekUndo()); // Restaura para o estado mais recente na stack
            campoTexto.setText(editor.getConteudo());
            areaTextoCopiado.setText("Histórico carregado. Digite e grave mais texto."); // Mensagem inicial
        } else {
            areaTextoCopiado.setText("Nenhum histórico encontrado. Digite e grave o texto.");
        }
    }

    private void adicionarListeners() {
        botaoGravar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textoAtual = campoTexto.getText();
                if (!textoAtual.isEmpty()) {
                    editor.setConteudo(textoAtual);
                    historico.adicionar(editor.salvar());

                    areaTextoCopiado.append("Gravado: \"" + textoAtual + "\"\n");

                    campoTexto.setText("");
                    JOptionPane.showMessageDialog(Main.this, "Texto gravado com sucesso!", "Gravar", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(Main.this, "O campo de texto está vazio!", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        botaoDesfazer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (historico.getUndoStackSize() > 1) {
                    historico.popUndo();
                    Memento mementoAnterior = historico.peekUndo();
                    editor.restaurar(mementoAnterior);
                    campoTexto.setText(editor.getConteudo());
                    areaTextoCopiado.setText("");
                    JOptionPane.showMessageDialog(Main.this, "Desfeito para a versão anterior.", "Undo", JOptionPane.INFORMATION_MESSAGE);
                } else if (historico.getUndoStackSize() == 1) {
                    historico.popUndo(); // Remove o último estado para esvaziar a stack
                    editor.setConteudo("");
                    campoTexto.setText("");
                    areaTextoCopiado.setText("");
                    JOptionPane.showMessageDialog(Main.this, "Desfeito para o estado inicial vazio.", "Undo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(Main.this, "Não há mais operações para desfazer.", "Undo", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        botaoClonar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Editor editorClonado = editor.clone();

                String textoOriginal = editor.getConteudo();
                editorClonado.setConteudo("Conteúdo clonado e modificado (original era: \"" + textoOriginal + "\")");

                JOptionPane.showMessageDialog(Main.this,
                    "Editor Original (estado atual): \"" + textoOriginal + "\"\n" +
                    "Editor Clonado (independentemente, já modificado): \"" + editorClonado.getConteudo() + "\"\n\n" +
                    "Este clone pode ser manipulado sem afetar o editor principal.",
                    "Demonstração do Prototype", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void salvarHistoricoNoDisco() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_HISTORICO))) {
            oos.writeObject(historico);
            System.out.println("Histórico salvo em " + ARQUIVO_HISTORICO);
        } catch (IOException e) {
            System.err.println("Erro ao salvar histórico: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Erro ao salvar histórico: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void carregarHistoricoDoDisco() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO_HISTORICO))) {
            historico = (Caretaker) ois.readObject();
            System.out.println("Histórico carregado de " + ARQUIVO_HISTORICO);
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de histórico (" + ARQUIVO_HISTORICO + ") não encontrado. Iniciando novo histórico.");
            historico = new Caretaker();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar histórico: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Erro ao carregar histórico: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            historico = new Caretaker();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().setVisible(true); // Cria uma instância da classe Main (que é a GUI)
            }
        });
    }
}