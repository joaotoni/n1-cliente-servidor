package org.cliservidor.novo;

import org.cliservidor.dao.AlunoDAO;
import org.cliservidor.model.Aluno;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DeletarAluno extends JFrame {
    private JComboBox<Aluno> selectAluno; // Aluno deve ser um objeto
    private JButton deletar;
    private JPanel deletarAluno;
    private Long matriculaSelecionada; // Para armazenar a matrícula do aluno selecionado

    public DeletarAluno() {
        setTitle("Deletar Aluno");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicializando o JPanel e os componentes
        deletarAluno = new JPanel();
        selectAluno = new JComboBox<>();
        deletar = new JButton("Deletar");

        // Carregar alunos no JComboBox
        AlunoDAO alunoDAO = new AlunoDAO();
        List<Aluno> alunos = alunoDAO.findAll();
        for (Aluno aluno : alunos) {
            selectAluno.addItem(aluno); // Adiciona o objeto Aluno
        }

        // Adicionando os componentes ao JPanel
        deletarAluno.add(new JLabel("Selecionar Aluno:"));
        deletarAluno.add(selectAluno);
        deletarAluno.add(deletar);

        // Adiciona o JPanel à JFrame
        add(deletarAluno);

        // Ação do botão deletar
        deletar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletarAluno();
            }
        });

        // Adicionando ActionListener para o JComboBox de alunos
        selectAluno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Aluno alunoSelecionado = (Aluno) selectAluno.getSelectedItem();
                if (alunoSelecionado != null) {
                    matriculaSelecionada = alunoSelecionado.getMatricula(); // Armazena a matrícula
                }
            }
        });
    }

    private void deletarAluno() {
        // Validação: Verifica se algum aluno foi selecionado
        if (matriculaSelecionada == null) {
            JOptionPane.showMessageDialog(null, "Por favor, selecione um aluno para deletar.");
            return;
        }

        // Deleta o aluno usando a matrícula
        AlunoDAO alunoDAO = new AlunoDAO();
        alunoDAO.delete(matriculaSelecionada); // Chama a função delete no DAO

        JOptionPane.showMessageDialog(null, "Aluno deletado com sucesso!");

        // Após deletar, remover o aluno do JComboBox
        selectAluno.removeItem(selectAluno.getSelectedItem());
    }
}
