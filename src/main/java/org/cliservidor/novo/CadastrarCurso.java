package org.cliservidor.novo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.cliservidor.dao.CursoDAO;
import org.cliservidor.model.Curso;

public class CadastrarCurso extends JFrame {
    private JTextField nomeCurso;
    private JTextField siglaCurso;
    private JComboBox<String> areaCurso;
    private JPanel cadastrarCurso;
    private JButton enviarCurso;

    public CadastrarCurso() {
        setTitle("Cadastrar Curso");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicializando o painel
        cadastrarCurso = new JPanel();
        add(cadastrarCurso); // Adiciona o JPanel à JFrame

        // Inicialize os componentes
        nomeCurso = new JTextField(20);
        siglaCurso = new JTextField(10);
        areaCurso = new JComboBox<>();
        enviarCurso = new JButton("Enviar");

        // Configurando o comboBox e adicionando os itens
        areaCurso.addItem("-");
        areaCurso.addItem("HUMANAS");
        areaCurso.addItem("EXATAS");
        areaCurso.addItem("BIOLOGICAS");
        areaCurso.addItem("ARTES");
        areaCurso.addItem("ESPORTES");

        // Configurando layout e adicionando os componentes ao painel
        cadastrarCurso.setLayout(new BoxLayout(cadastrarCurso, BoxLayout.Y_AXIS));
        cadastrarCurso.add(new JLabel("Nome do Curso:"));
        cadastrarCurso.add(nomeCurso);
        cadastrarCurso.add(new JLabel("Sigla do Curso:"));
        cadastrarCurso.add(siglaCurso);
        cadastrarCurso.add(new JLabel("Área do Curso:"));
        cadastrarCurso.add(areaCurso);
        cadastrarCurso.add(enviarCurso);

        // Adiciona ação ao botão
        enviarCurso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarCurso();
            }
        });
    }

    private void enviarCurso() {
        String nome = nomeCurso.getText();
        String sigla = siglaCurso.getText();
        String area = (String) areaCurso.getSelectedItem();

        // Validações
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome não pode estar em branco.");
            return;
        }
        if (sigla.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Sigla não pode estar em branco.");
            return;
        }
        if (area.equals("-")) {
            JOptionPane.showMessageDialog(this, "Área inválida.");
            return;
        }

        // Criação do curso e envio ao banco de dados
        Curso curso = new Curso();
        curso.setNome(nome);
        curso.setSigla(sigla);
        curso.setArea(area);

        CursoDAO cursoDAO = new CursoDAO();
        cursoDAO.create(curso);

        JOptionPane.showMessageDialog(this, "Curso cadastrado com sucesso!");
    }
}
