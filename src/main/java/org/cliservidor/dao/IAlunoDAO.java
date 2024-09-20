package org.cliservidor.dao;

import org.cliservidor.model.Aluno;

import java.util.List;

public interface IAlunoDAO {
    Aluno create(Aluno aluno);
    void update(Aluno aluno);
    void delete(Long matricula);
    Aluno findById(Long matricula);
    List<Aluno> findAll();
}
