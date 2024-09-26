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

        listarAlunosPanel = new JPanel(new BorderLayout());
        alunosTable = new JTable();
        scrollPane = new JScrollPane(alunosTable);

        carregarAlunos();

        listarAlunosPanel.add(scrollPane, BorderLayout.CENTER);

        add(listarAlunosPanel);
    }

    private void carregarAlunos() {
        String[] colunas = {"Matrícula", "Nome", "Maioridade", "Sexo", "Curso"};
        DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);

        AlunoDAO alunoDAO = new AlunoDAO();
        List<Aluno> alunos = alunoDAO.findAll();

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
