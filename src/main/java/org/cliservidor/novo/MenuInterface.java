package org.cliservidor.novo;

import javax.swing.*;

public class MenuInterface extends JFrame {
    private JButton alunosCadastradosButton;
    private JButton cursosCadastradosButton;
    private JButton cadastrarAlunoButton;
    private JButton cadastrarCursoButton;
    private JButton atualizarAlunoButton;
    private JButton deletarCursoButton;
    private JButton deletarAlunoButton;
    private JButton atualizarCursoButton;
    private JPanel menuPanel;

    public MenuInterface() {
        setTitle("Menu Principal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        menuPanel = new JPanel();
        add(menuPanel);

        cadastrarCursoButton = new JButton("Cadastrar Curso");
        cadastrarAlunoButton = new JButton("Cadastrar Aluno");
        atualizarAlunoButton = new JButton("Atualizar Aluno");
        atualizarCursoButton = new JButton("Atualizar Curso");
        deletarCursoButton = new JButton("Deletar Curso");
        deletarAlunoButton = new JButton("Deletar Aluno");
        cursosCadastradosButton = new JButton("Listar Cursos");
        alunosCadastradosButton = new JButton("Listar Alunos");

        menuPanel.add(cadastrarCursoButton);
        menuPanel.add(cadastrarAlunoButton);
        menuPanel.add(atualizarAlunoButton);
        menuPanel.add(atualizarCursoButton);
        menuPanel.add(deletarCursoButton);
        menuPanel.add(deletarAlunoButton);
        menuPanel.add(cursosCadastradosButton);
        menuPanel.add(alunosCadastradosButton);

        setActionListeners();
    }

    private void setActionListeners() {
        cadastrarCursoButton.addActionListener(e -> {
            CadastrarCurso cadastrarCursoFrame = new CadastrarCurso();
            cadastrarCursoFrame.setVisible(true);
        });
        cadastrarAlunoButton.addActionListener(e -> {
            CadastroAluno cadastrarAlunoFrame = new CadastroAluno();
            cadastrarAlunoFrame.setVisible(true);
        });
        atualizarAlunoButton.addActionListener(e -> {
            AtualizarAluno atualizarAlunoFrame = new AtualizarAluno();
            atualizarAlunoFrame.setVisible(true);
        });
        atualizarCursoButton.addActionListener(e -> {
            AtualizarCurso atualizarCursoFrame = new AtualizarCurso();
            atualizarCursoFrame.setVisible(true);
        });
        deletarCursoButton.addActionListener(e -> {
            DeletarCurso deletarCursoFrame = new DeletarCurso();
            deletarCursoFrame.setVisible(true);
        });
        deletarAlunoButton.addActionListener(e -> {
            DeletarAluno deletarAlunoFrame = new DeletarAluno();
            deletarAlunoFrame.setVisible(true);
        });
        cursosCadastradosButton.addActionListener(e -> {
            ListarCursos listarCursosFrame = new ListarCursos();
            listarCursosFrame.setVisible(true);
        });
        alunosCadastradosButton.addActionListener(e -> {
            ListarAlunos listarAlunosFrame = new ListarAlunos();
            listarAlunosFrame.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuInterface().setVisible(true));
    }
}
