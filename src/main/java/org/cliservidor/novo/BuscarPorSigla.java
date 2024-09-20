package org.cliservidor.novo;

import org.cliservidor.dao.CursoDAO;
import org.cliservidor.model.Curso;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BuscarPorSigla extends JFrame {
    private JPanel buscarCursoPanel;
    private JTable cursoTable;
    private JScrollPane scrollPane;
    private JTextField siglaTextField;
    private JButton buscarButton;

    public BuscarPorSigla() {
        setTitle("Buscar Curso por Sigla");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicializando o painel e os componentes
        buscarCursoPanel = new JPanel(new BorderLayout());
        cursoTable = new JTable();
        scrollPane = new JScrollPane(cursoTable);

        // Campo de texto e botão de busca
        siglaTextField = new JTextField(10);
        buscarButton = new JButton("Buscar");

        // Painel superior para o campo de texto e botão de busca
        JPanel buscarPanel = new JPanel();
        buscarPanel.add(new JLabel("Sigla:"));
        buscarPanel.add(siglaTextField);
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
                buscarCursoPorSigla();
            }
        });
    }

    private void buscarCursoPorSigla() {
        String sigla = siglaTextField.getText().trim();

        // Verifica se o campo de sigla está vazio
        if (sigla.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite uma sigla válida.");
            return;
        }

        // Chama o método para buscar o curso no banco de dados
        CursoDAO cursoDAO = new CursoDAO();
        Curso curso = cursoDAO.findBySigla(sigla); // Alterado para Curso

        // Definir as colunas da tabela
        String[] colunas = {"Código", "Nome", "Sigla", "Área"};
        DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);

        // Verifica se algum curso foi encontrado
        if (curso == null) { // Alterado para verificar se curso é nulo
            JOptionPane.showMessageDialog(null, "Nenhum curso encontrado com essa sigla.");
        } else {
            // Adicionar os dados do curso à tabela
            Object[] dadosCurso = {
                    curso.getCodigo(),
                    curso.getNome(),
                    curso.getSigla(),
                    curso.getArea()
            };
            modeloTabela.addRow(dadosCurso);
        }

        // Definir o modelo da tabela
        cursoTable.setModel(modeloTabela);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BuscarPorSigla().setVisible(true);
            }
        });
    }
}
