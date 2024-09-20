package org.cliservidor.novo;

import org.cliservidor.dao.CursoDAO;
import org.cliservidor.model.Curso;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BuscarPorCodigo extends JFrame {
    private JPanel buscarCursoPanel;
    private JTable cursoTable;
    private JScrollPane scrollPane;
    private JTextField codigoTextField;
    private JButton buscarButton;

    public BuscarPorCodigo() {
        setTitle("Buscar Curso por Código");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicializando o painel e os componentes
        buscarCursoPanel = new JPanel(new BorderLayout());
        cursoTable = new JTable();
        scrollPane = new JScrollPane(cursoTable);

        // Campo de texto e botão de busca
        codigoTextField = new JTextField(10);
        buscarButton = new JButton("Buscar");

        // Painel superior para o campo de texto e botão de busca
        JPanel buscarPanel = new JPanel();
        buscarPanel.add(new JLabel("Código:"));
        buscarPanel.add(codigoTextField);
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
                buscarCursoPorCodigo();
            }
        });
    }

    private void buscarCursoPorCodigo() {
        String codigoTexto = codigoTextField.getText();

        // Verifica se o campo de código está vazio
        if (codigoTexto.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite um código válido.");
            return;
        }

        try {
            // Converte o código para long (assumindo que o código é do tipo long)
            long codigo = Long.parseLong(codigoTexto);

            // Chama o método para buscar o curso no banco de dados
            CursoDAO cursoDAO = new CursoDAO();
            Curso curso = cursoDAO.findById(codigo);

            // Definir as colunas da tabela
            String[] colunas = {"Código", "Nome", "Sigla", "Área"};
            DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);

            // Verifica se o curso foi encontrado
            if (curso != null) {
                // Adicionar os dados do curso à tabela
                Object[] dadosCurso = {
                        curso.getCodigo(),
                        curso.getNome(),
                        curso.getSigla(),
                        curso.getArea()
                };
                modeloTabela.addRow(dadosCurso);
            } else {
                JOptionPane.showMessageDialog(null, "Curso não encontrado.");
            }

            // Definir o modelo da tabela
            cursoTable.setModel(modeloTabela);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Código inválido. Insira um número.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BuscarPorCodigo().setVisible(true);
            }
        });
    }
}
