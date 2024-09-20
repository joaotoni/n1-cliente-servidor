package org.cliservidor.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.cliservidor.model.Area;
import org.cliservidor.config.ConnectionFactory;
import org.cliservidor.model.Curso;

public class CursoDAO implements ICursoDAO {

    @Override
    public Curso create(Curso curso) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "INSERT INTO curso (nome, sigla, area) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, curso.getNome());
            stmt.setString(2, curso.getSigla());
            stmt.setString(3, curso.getArea());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                curso.setCodigo(generatedKeys.getLong(1)); // Atualiza o código do curso com a chave gerada
            }

            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return curso;
    }

    @Override
    public void update(Curso curso) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "UPDATE curso SET nome = ?, sigla = ?, area = ? WHERE codigo = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, curso.getNome());
            stmt.setString(2, curso.getSigla());
            stmt.setString(3, curso.getArea());
            stmt.setLong(4, curso.getCodigo());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long codigo) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "DELETE FROM curso WHERE codigo = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, codigo);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Curso findById(Long codigo) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "SELECT * FROM curso WHERE codigo = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, codigo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Curso curso = new Curso();
                curso.setCodigo(rs.getLong("codigo"));
                curso.setNome(rs.getString("nome"));
                curso.setSigla(rs.getString("sigla"));
                curso.setArea(rs.getString("area"));
                return curso;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<String> buscarSiglasCursos() {
        List<String> siglas = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "SELECT sigla FROM curso";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                siglas.add(rs.getString("sigla"));
            }

            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return siglas;
    }

    @Override
    public List<Curso> findAll() {
        List<Curso> cursos = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "SELECT * FROM curso";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Curso curso = new Curso();
                curso.setCodigo(rs.getLong("codigo"));
                curso.setNome(rs.getString("nome"));
                curso.setSigla(rs.getString("sigla"));
                curso.setArea(rs.getString("area"));
                cursos.add(curso);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cursos;
    }

    @Override
    public List<Curso> findByArea(Area area) {
        List<Curso> cursos = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "SELECT * FROM curso WHERE area = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, area.name());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Curso curso = new Curso();
                curso.setCodigo(rs.getLong("codigo"));
                curso.setNome(rs.getString("nome"));
                curso.setSigla(rs.getString("sigla"));
                curso.setArea(rs.getString("area"));
                cursos.add(curso);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cursos;
    }

    @Override
    public Curso findBySigla(String sigla) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "SELECT * FROM curso WHERE sigla = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, sigla);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Curso curso = new Curso();
                curso.setCodigo(rs.getLong("codigo"));
                curso.setNome(rs.getString("nome"));
                curso.setSigla(rs.getString("sigla"));
                curso.setArea(rs.getString("area"));
                return curso;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
