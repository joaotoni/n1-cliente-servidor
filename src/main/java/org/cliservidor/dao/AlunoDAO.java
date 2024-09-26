package org.cliservidor.dao;

import org.cliservidor.config.ConnectionFactory;
import org.cliservidor.model.Aluno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO implements IAlunoDAO {

    private Connection connection;

    public Aluno create(Aluno aluno) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "INSERT INTO aluno (nome, maioridade, sexo, curso) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, aluno.getNome());
            stmt.setBoolean(2, aluno.isMaioridade());
            stmt.setString(3, aluno.getSexo());
            stmt.setString(4, aluno.getCurso());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return aluno;
    }

    public void update(Aluno aluno) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "UPDATE aluno SET nome = ?, maioridade = ?, sexo = ?, curso = ? WHERE matricula = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, aluno.getNome());
            stmt.setBoolean(2, aluno.isMaioridade());
            stmt.setString(3, aluno.getSexo());
            stmt.setString(4, aluno.getCurso());
            stmt.setLong(5, aluno.getMatricula());
            stmt.executeUpdate();
            stmt.close();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Long matricula) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "DELETE FROM aluno WHERE matricula = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, matricula);
            stmt.executeUpdate();
            stmt.close();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Aluno findById(Long matricula) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "SELECT * FROM aluno WHERE matricula = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, matricula);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setMatricula(rs.getLong("matricula"));
                aluno.setNome(rs.getString("nome"));
                aluno.setMaioridade(rs.getBoolean("maioridade"));
                aluno.setSexo(rs.getString("sexo"));
                return aluno;
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Aluno> findAll() {
        List<Aluno> alunos;
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "SELECT * FROM aluno";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            alunos = new ArrayList<>();

            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setMatricula(rs.getLong("matricula"));
                aluno.setNome(rs.getString("nome"));
                aluno.setMaioridade(rs.getBoolean("maioridade"));
                aluno.setSexo(rs.getString("sexo"));
                aluno.setCurso(rs.getString("curso"));
                alunos.add(aluno);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return alunos;
    }
}
