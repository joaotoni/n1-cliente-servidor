package org.cliservidor.dao;

import org.cliservidor.model.Area;
import org.cliservidor.model.Curso;

import java.util.List;

public interface ICursoDAO {
    Curso create(Curso curso);
    void update(Curso curso);
    void delete(Long codigo);
    Curso findById(Long codigo);

    List<String> buscarSiglasCursos();

    List<Curso> findAll();
    List<Curso> findByArea(Area area);
    Curso findBySigla(String sigla);
}
