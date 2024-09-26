package org.cliservidor.novo;

import org.cliservidor.dao.CursoDAO;
import org.cliservidor.model.Curso;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AtualizarCurso extends JFrame {
    private JTextField nameCurso;
    private JTextField siglaCurso;
    private JComboBox<String> areaCurso; // Use <String> para tipo seguro
    private JButton enviarUpdateCurso;
    private JPanel atualizarCurso;
    private JComboBox<Curso> selectCurso; // Altera para <Curso>

    public AtualizarCurso() {
        setTitle("Atualizar Curso");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicialize o JPanel e adicione-o à JFrame
        atualizarCurso = new JPanel();
        add(atualizarCurso);

        // Inicialize os componentes
        nameCurso = new JTextField(20);
        siglaCurso = new JTextField(10);
        areaCurso = new JComboBox<>();
        enviarUpdateCurso = new JButton("Atualizar");
        selectCurso = new JComboBox<>(); // ComboBox para selecionar o curso

        // Configuração do JComboBox de áreas
        areaCurso.addItem("-");
        areaCurso.addItem("Humanas");
        areaCurso.addItem("Exatas");
        areaCurso.addItem("Biológicas");
        areaCurso.addItem("Artes");
        areaCurso.addItem("Esportes");

        // Carregando cursos no JComboBox
        CursoDAO cursoDAO = new CursoDAO();
        List<Curso> cursos = cursoDAO.findAll();
        for (Curso curso : cursos) {
            selectCurso.addItem(curso); // Adiciona o objeto Curso
        }

        // Adicione os componentes ao painel
        atualizarCurso.add(new JLabel("Selecionar Curso:"));
        atualizarCurso.add(selectCurso);
        atualizarCurso.add(new JLabel("Nome do Curso:"));
        atualizarCurso.add(nameCurso);
        atualizarCurso.add(new JLabel("Sigla do Curso:"));
        atualizarCurso.add(siglaCurso);
        atualizarCurso.add(new JLabel("Área do Curso:"));
        atualizarCurso.add(areaCurso);
        atualizarCurso.add(enviarUpdateCurso);

        // Ação do JComboBox para carregar os dados do curso selecionado
        selectCurso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Curso cursoSelecionado = (Curso) selectCurso.getSelectedItem();
                if (cursoSelecionado != null) {
                    nameCurso.setText(cursoSelecionado.getNome());
                    siglaCurso.setText(cursoSelecionado.getSigla());
                    areaCurso.setSelectedItem(cursoSelecionado.getArea());
                }
            }
        });

        // Ação do botão
        enviarUpdateCurso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarCurso();
            }
        });
    }

    private void atualizarCurso() {
        String nome = nameCurso.getText();
        String sigla = siglaCurso.getText();
        String area = (String) areaCurso.getSelectedItem();
        Curso cursoSelecionado = (Curso) selectCurso.getSelectedItem();

        // Validações
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nome não pode estar em branco.");
            return;
        }
        if (sigla.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Sigla não pode estar em branco.");
            return;
        }
        if (area.equals("-")) {
            JOptionPane.showMessageDialog(null, "Área inválida.");
            return;
        }
        if (cursoSelecionado == null) {
            JOptionPane.showMessageDialog(null, "Curso inválido.");
            return;
        }

        // Crie um objeto Curso com os dados atualizados
        cursoSelecionado.setNome(nome);
        cursoSelecionado.setSigla(sigla);
        cursoSelecionado.setArea(area);

        // Atualizar o curso no banco de dados
        CursoDAO cursoDAO = new CursoDAO();
        cursoDAO.update(cursoSelecionado); // Chama o método update

        JOptionPane.showMessageDialog(null, "Curso atualizado com sucesso!");
    }
}
