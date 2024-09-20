package org.cliservidor.novo;

import org.cliservidor.dao.CursoDAO;
import org.cliservidor.model.Curso;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DeletarCurso extends JFrame {
    private JComboBox<Curso> selectCurso; // Use <Curso> para carregar objetos Curso
    private JButton buttonDelete;
    private JPanel deletarCurso;
    private Long cursoSelecionadoId; // Para armazenar o ID do curso selecionado

    public DeletarCurso() {
        setTitle("Deletar Curso");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicializando o JPanel e os componentes
        deletarCurso = new JPanel();
        selectCurso = new JComboBox<>();
        buttonDelete = new JButton("Deletar");

        // Carregar cursos no JComboBox
        CursoDAO cursoDAO = new CursoDAO();
        List<Curso> cursos = cursoDAO.findAll();
        for (Curso curso : cursos) {
            selectCurso.addItem(curso); // Adiciona o objeto Curso
        }

        // Adicionando os componentes ao JPanel
        deletarCurso.add(new JLabel("Selecionar Curso:"));
        deletarCurso.add(selectCurso);
        deletarCurso.add(buttonDelete);

        // Adiciona o JPanel à JFrame
        add(deletarCurso);

        // Ação do botão
        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletarCurso();
            }
        });

        // Ação do JComboBox para capturar o curso selecionado
        selectCurso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Curso cursoSelecionado = (Curso) selectCurso.getSelectedItem();
                if (cursoSelecionado != null) {
                    cursoSelecionadoId = cursoSelecionado.getCodigo(); // Armazena o ID do curso selecionado
                }
            }
        });
    }

    private void deletarCurso() {
        // Validação: Verifica se algum curso foi selecionado
        if (cursoSelecionadoId == null) {
            JOptionPane.showMessageDialog(null, "Por favor, selecione um curso para deletar.");
            return;
        }

        // Deleta o curso usando o ID
        CursoDAO cursoDAO = new CursoDAO();
        cursoDAO.delete(cursoSelecionadoId); // Chama a função delete no DAO

        JOptionPane.showMessageDialog(null, "Curso deletado com sucesso!");

        // Após deletar, remover o curso do JComboBox
        selectCurso.removeItem(selectCurso.getSelectedItem());
    }
}
