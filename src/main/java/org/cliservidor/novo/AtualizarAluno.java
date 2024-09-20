package org.cliservidor.novo;

import org.cliservidor.dao.AlunoDAO;
import org.cliservidor.dao.CursoDAO; // Importa o CursoDAO
import org.cliservidor.model.Aluno;
import org.cliservidor.model.Curso;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AtualizarAluno extends JFrame {
    private JTextField atualizarNome;
    private JComboBox<String> selectCurso;
    private JButton atualizarRegistro;
    private JPanel atualizarAluno;
    private JComboBox<Aluno> selectAluno; // Aluno deve ser um objeto
    private Long matriculaSelecionada; // Para armazenar a matrícula do aluno selecionado

    public AtualizarAluno() {
        setTitle("Atualizar Aluno");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicialize o JPanel e os componentes
        atualizarAluno = new JPanel();
        atualizarNome = new JTextField(20);
        selectCurso = new JComboBox<>();
        atualizarRegistro = new JButton("Atualizar");
        selectAluno = new JComboBox<>();

        // Carregando alunos no JComboBox
        AlunoDAO alunoDAO = new AlunoDAO();
        List<Aluno> alunos = alunoDAO.findAll();
        for (Aluno aluno : alunos) {
            selectAluno.addItem(aluno); // Adiciona o objeto Aluno
        }

        // Carregando cursos no JComboBox
        CursoDAO cursoDAO = new CursoDAO();
        List<Curso> cursos = cursoDAO.findAll();
        selectCurso.addItem("-"); // Adiciona a opção padrão
        for (Curso curso : cursos) {
            selectCurso.addItem(curso.getNome()); // Adiciona o nome do curso
        }

        // Adicionando os componentes ao JPanel
        atualizarAluno.add(new JLabel("Nome do Aluno:"));
        atualizarAluno.add(atualizarNome);
        atualizarAluno.add(new JLabel("Selecionar Curso:"));
        atualizarAluno.add(selectCurso);
        atualizarAluno.add(new JLabel("Selecionar Aluno:"));
        atualizarAluno.add(selectAluno);
        atualizarAluno.add(atualizarRegistro);

        // Adiciona o JPanel à JFrame
        add(atualizarAluno);

        // Ação do botão
        atualizarRegistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarAluno();
            }
        });

        // Adicionando ActionListener para o JComboBox de alunos
        selectAluno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Aluno alunoSelecionado = (Aluno) selectAluno.getSelectedItem();
                if (alunoSelecionado != null) {
                    atualizarNome.setText(alunoSelecionado.getNome());
                    selectCurso.setSelectedItem(alunoSelecionado.getCurso()); // Ajuste necessário se você tiver uma lista de cursos
                    matriculaSelecionada = alunoSelecionado.getMatricula(); // Armazena a matrícula
                }
            }
        });
    }

    private void atualizarAluno() {
        String nome = atualizarNome.getText();
        String selectCursos = (String) selectCurso.getSelectedItem();

        // Validações
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nome não pode estar em branco.");
            return;
        }
        if (selectCursos.equals("-")) {
            JOptionPane.showMessageDialog(null, "Curso inválido.");
            return;
        }

        // Crie um objeto Aluno com os dados atualizados
        Aluno alunoAtualizado = new Aluno();
        alunoAtualizado.setMatricula(matriculaSelecionada);
        alunoAtualizado.setNome(nome);
        alunoAtualizado.setCurso(selectCursos); // Define o curso selecionado

        // Atualizar o aluno no banco de dados
        AlunoDAO alunoDAO = new AlunoDAO();
        alunoDAO.update(alunoAtualizado); // Chama a função update.

        JOptionPane.showMessageDialog(null, "Aluno atualizado com sucesso!");
    }
}
