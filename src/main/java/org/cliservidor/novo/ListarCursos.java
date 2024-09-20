package org.cliservidor.novo;

import org.cliservidor.dao.CursoDAO;
import org.cliservidor.model.Curso;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListarCursos extends JFrame {
    private JPanel listarCursosPanel;
    private JTable cursosTable;
    private JScrollPane scrollPane;

    public ListarCursos() {
        setTitle("Listar Todos os Cursos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicializando o painel e a tabela
        listarCursosPanel = new JPanel(new BorderLayout());
        cursosTable = new JTable();
        scrollPane = new JScrollPane(cursosTable);

        // Carregar cursos no JTable
        carregarCursos();

        // Adicionar componentes ao painel
        listarCursosPanel.add(scrollPane, BorderLayout.CENTER);

        // Adicionar o painel à JFrame
        add(listarCursosPanel);
    }

    private void carregarCursos() {
        // Definir as colunas da tabela
        String[] colunas = {"Código", "Nome", "Sigla", "Área"};
        DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);

        // Buscar a lista de cursos do banco de dados
        CursoDAO cursoDAO = new CursoDAO();
        List<Curso> cursos = cursoDAO.findAll();

        // Adicionar cada curso como uma linha na tabela
        for (Curso curso : cursos) {
            Object[] dadosCurso = {
                    curso.getCodigo(),
                    curso.getNome(),
                    curso.getSigla(),
                    curso.getArea()
            };
            modeloTabela.addRow(dadosCurso);
        }

        // Definir o modelo da tabela
        cursosTable.setModel(modeloTabela);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ListarCursos().setVisible(true);
            }
        });
    }
}
