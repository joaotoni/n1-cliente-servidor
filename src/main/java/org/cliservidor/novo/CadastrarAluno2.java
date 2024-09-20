package org.cliservidor.novo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import org.cliservidor.dao.AlunoDAO;
import org.cliservidor.dao.CursoDAO;
import org.cliservidor.model.Aluno;
import org.cliservidor.model.Curso;

public class CadastrarAluno2 extends JFrame {
    private JTextField name;
    private JRadioButton Yes;
    private JRadioButton No;
    private JComboBox<String> sexo;
    private JComboBox<String> curse;
    private JButton cadastrarButton;
    private JPanel cadastrarAluno;
    private JPanel maioridadeRadio;

    public CadastrarAluno2() {
        setTitle("Cadastrar Aluno");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicialize o painel
        cadastrarAluno = new JPanel();
        add(cadastrarAluno);

        // Inicialize os componentes
        name = new JTextField(20);
        sexo = new JComboBox<>();
        sexo.addItem("-");
        sexo.addItem("Mulher");
        sexo.addItem("Homem");
        sexo.addItem("Transexual");

        curse = new JComboBox<>();
        // Adicione opções de curso aqui
        curse.addItem("-");

        // Chama o função para carregar as siglas dos cursos no JComboBox
        carregarCursos();

        cadastrarButton = new JButton("Cadastrar");

        // Adicione os componentes ao painel
        cadastrarAluno.add(new JLabel("Nome:"));
        cadastrarAluno.add(name);
        cadastrarAluno.add(new JLabel("Sexo:"));
        cadastrarAluno.add(sexo);
        cadastrarAluno.add(new JLabel("Curso:"));
        cadastrarAluno.add(curse);
        cadastrarAluno.add(cadastrarButton);

        // Ação do botão
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarPessoa();
            }
        });
    }

    // Método para carregar as siglas dos cursos do banco de dados
    private void carregarCursos() {
        CursoDAO cursoDAO = new CursoDAO();
        List<Curso> cursos = cursoDAO.findAll();


        for (Curso curso : cursos) {
            curse.addItem(curso.getNome());
        }
    }

    private void cadastrarPessoa() {
        String nome = name.getText();
        String sexoSelecionado = (String) sexo.getSelectedItem();
        boolean maioridade = Yes != null && Yes.isSelected();
        String curso = (String) curse.getSelectedItem();

        // Validações
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nome não pode estar em branco.");
            return;
        }
        if (sexoSelecionado.equals("-")) {
            JOptionPane.showMessageDialog(null, "Sexo inválido.");
            return;
        }
        if (curso.equals("-")) {
            JOptionPane.showMessageDialog(null, "Curso inválido.");
            return;
        }

        // Criação do aluno e envio ao banco de dados
        Aluno aluno = new Aluno();
        aluno.setNome(nome);
        aluno.setSexo(sexoSelecionado);
        aluno.setMaioridade(maioridade);
        aluno.setCurso(curso);

        AlunoDAO alunoDAO = new AlunoDAO();
        alunoDAO.create(aluno); // Envia o aluno para o banco de dados

        JOptionPane.showMessageDialog(null, "Aluno cadastrado com sucesso!");
    }
}
