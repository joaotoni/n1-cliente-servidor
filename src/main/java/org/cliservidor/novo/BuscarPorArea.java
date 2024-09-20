package org.cliservidor.novo;

import org.cliservidor.dao.CursoDAO;
import org.cliservidor.model.Curso;
import org.cliservidor.model.Area;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BuscarPorArea extends JFrame {
    private JPanel buscarCursoPanel;
    private JTable cursoTable;
    private JScrollPane scrollPane;
    private JComboBox<Area> areaComboBox; // Alterado para JComboBox de áreas
    private JButton buscarButton;

    public BuscarPorArea() {
        setTitle("Buscar Curso por Área");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicializando o painel e os componentes
        buscarCursoPanel = new JPanel(new BorderLayout());
        cursoTable = new JTable();
        scrollPane = new JScrollPane(cursoTable);

        // ComboBox e botão de busca
        areaComboBox = new JComboBox<>(Area.values()); // Preenche com as áreas
        buscarButton = new JButton("Buscar");

        // Painel superior para o comboBox e botão de busca
        JPanel buscarPanel = new JPanel();
        buscarPanel.add(new JLabel("Área:"));
        buscarPanel.add(areaComboBox);
        buscarPanel.add(buscarButton);

        // Adicionar componentes ao painel principal
        buscarCursoPanel.add(buscarPanel, BorderLayout.NORTH);
        buscarCursoPanel.add(scrollPane, BorderLayout.CENTER);

        // Adicionar o painel à JFrame
        add(buscarCursoPanel);

        // Ação do botão de buscar
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarCursoPorArea();
            }
        });
    }

    private void buscarCursoPorArea() {
        Area areaSelecionada = (Area) areaComboBox.getSelectedItem();

        // Chama o método para buscar cursos no banco de dados pela área
        CursoDAO cursoDAO = new CursoDAO();
        List<Curso> cursos = cursoDAO.findByArea(areaSelecionada); // Busca pela área selecionada

        // Definir as colunas da tabela
        String[] colunas = {"Código", "Nome", "Sigla", "Área"};
        DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);

        // Verifica se algum curso foi encontrado
        if (cursos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum curso encontrado para a área selecionada.");
        } else {
            // Adicionar os dados dos cursos à tabela
            for (Curso curso : cursos) {
                Object[] dadosCurso = {
                        curso.getCodigo(),
                        curso.getNome(),
                        curso.getSigla(),
                        curso.getArea()
                };
                modeloTabela.addRow(dadosCurso);
            }
        }

        // Definir o modelo da tabela
        cursoTable.setModel(modeloTabela);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BuscarPorArea().setVisible(true);
            }
        });
    }
}
