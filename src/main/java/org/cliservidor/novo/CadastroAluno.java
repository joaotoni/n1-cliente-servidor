package org.cliservidor.novo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import org.cliservidor.dao.AlunoDAO;
import org.cliservidor.dao.CursoDAO;
import org.cliservidor.model.Aluno;

public class CadastroAluno extends JFrame {
    private JTextField nomeCompleto;
    private JRadioButton simRadioButton;
    private JRadioButton naoRadioButton;
    private JComboBox<String> sexoComboBox;
    private JComboBox<String> cursoComboBox;
    private JButton cadastrar;
    private ButtonGroup maioridade;

    public CadastroAluno() {
        setTitle("Cadastro de Aluno");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicialize os componentes do formulário
        nomeCompleto = new JTextField(20);
        sexoComboBox = new JComboBox<>();
        sexoComboBox.addItem("-");
        sexoComboBox.addItem("Mulher");
        sexoComboBox.addItem("Homem");
        sexoComboBox.addItem("Transexual");

        cursoComboBox = new JComboBox<>();
        cursoComboBox.addItem("-");

        // Inicialize os botões de rádio para maioridade
        simRadioButton = new JRadioButton("Sim");
        naoRadioButton = new JRadioButton("Não");
        maioridade = new ButtonGroup();
        maioridade.add(simRadioButton);
        maioridade.add(naoRadioButton);

        cadastrar = new JButton("Cadastrar");

        // Painel principal
        JPanel painelCadastro = new JPanel();
        add(painelCadastro);

        // Adiciona os componentes ao painel
        painelCadastro.add(new JLabel("Nome completo:"));
        painelCadastro.add(nomeCompleto);
        painelCadastro.add(new JLabel("Sexo:"));
        painelCadastro.add(sexoComboBox);
        painelCadastro.add(new JLabel("Maioridade:"));
        painelCadastro.add(simRadioButton);
        painelCadastro.add(naoRadioButton);
        painelCadastro.add(new JLabel("Curso:"));
        painelCadastro.add(cursoComboBox);
        painelCadastro.add(cadastrar);

        // Chama o método para carregar as siglas dos cursos no JComboBox
        carregarCursos();

        // Ação do botão cadastrar
        cadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarPessoa();
            }
        });
    }

    // Método para carregar as siglas dos cursos do banco de dados
    private void carregarCursos() {
        CursoDAO cursoDAO = new CursoDAO();
        List<String> siglas = cursoDAO.buscarSiglasCursos();

        // Adiciona as siglas dos cursos no JComboBox
        for (String sigla : siglas) {
            cursoComboBox.addItem(sigla);
        }
    }

    // Método para cadastrar a pessoa
    private void cadastrarPessoa() {
        String nome = nomeCompleto.getText();
        String sexoSelecionado = (String) sexoComboBox.getSelectedItem();
        boolean maioridade = simRadioButton.isSelected(); // Verifica se o 'Sim' foi selecionado
        String curso = (String) cursoComboBox.getSelectedItem();

        // Validações
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nome não pode estar em branco.");
            return;
        }
        if (sexoSelecionado.equals("-")) {
            JOptionPane.showMessageDialog(null, "Sexo inválido.");
            return;
        }
        if (!simRadioButton.isSelected() && !naoRadioButton.isSelected()) {
            JOptionPane.showMessageDialog(null, "Selecione a maioridade.");
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

    // Método principal para testar a interface
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CadastroAluno().setVisible(true);
            }
        });
    }
}
