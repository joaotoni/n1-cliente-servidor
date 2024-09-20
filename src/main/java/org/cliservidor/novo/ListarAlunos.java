package org.cliservidor.novo;

import org.cliservidor.dao.AlunoDAO;
import org.cliservidor.model.Aluno;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListarAlunos extends JFrame {
    private JPanel listarAlunosPanel;
    private JTable alunosTable;
    private JScrollPane scrollPane;

    public ListarAlunos() {
        setTitle("Listar Todos os Alunos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicializando o painel e a tabela
        listarAlunosPanel = new JPanel(new BorderLayout());
        alunosTable = new JTable();
        scrollPane = new JScrollPane(alunosTable);

        // Carregar alunos no JTable
        carregarAlunos();

        // Adicionar componentes ao painel
        listarAlunosPanel.add(scrollPane, BorderLayout.CENTER);

        // Adicionar o painel à JFrame
        add(listarAlunosPanel);
    }

    private void carregarAlunos() {
        // Definir as colunas da tabela
        String[] colunas = {"Matrícula", "Nome", "Maioridade", "Sexo", "Curso"};
        DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);

        // Buscar a lista de alunos do banco de dados
        AlunoDAO alunoDAO = new AlunoDAO();
        List<Aluno> alunos = alunoDAO.findAll();

        // Adicionar cada aluno como uma linha na tabela
        for (Aluno aluno : alunos) {
            Object[] dadosAluno = {
                    aluno.getMatricula(),
                    aluno.getNome(),
                    aluno.isMaioridade() ? "Sim" : "Não",
                    aluno.getSexo(),
                    aluno.getCurso()
            };
            modeloTabela.addRow(dadosAluno);
        }

        // Definir o modelo da tabela
        alunosTable.setModel(modeloTabela);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ListarAlunos().setVisible(true);
            }
        });
    }
}
