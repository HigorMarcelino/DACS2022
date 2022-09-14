package br.univille.apidacs2022.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.univille.apidacs2022.service.ConsultaService;
import br.univille.coredacs2022.entity.Consulta;
import br.univille.coredacs2022.repository.ConsultaRepository;

@Service
public class ConsultaServiceImpl implements ConsultaService{
    @Autowired
    private ConsultaRepository repository;

    @Override
    public List<Consulta> getAll() {
        return repository.findAll();
    }

    @Override
    public Consulta save(Consulta consulta) {
        repository.save(consulta);
        return consulta;
    }

    @Override
    public Consulta findById(long id) {
        Optional<Consulta> consulta = repository.findById(id);
        if(consulta.isPresent()){
            return consulta.get();
        }
        return null;
    }

    @Override
    public Consulta delete(long id) {
        Optional<Consulta> consulta = repository.findById(id);
        if(consulta.isPresent()){
            var pac = consulta.get();
            repository.delete(pac);
            return pac;
        }
        return null;
    }
}
