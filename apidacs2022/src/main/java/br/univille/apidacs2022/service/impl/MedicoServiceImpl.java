package br.univille.apidacs2022.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.univille.coredacs2022.repository.MedicoRepository;
import br.univille.apidacs2022.service.MedicoService;
import br.univille.coredacs2022.entity.Medico;

@Service
public class MedicoServiceImpl implements MedicoService{
    @Autowired
    private MedicoRepository repository;

    @Override
    public List<Medico> getAll() {
        return repository.findAll();
    }

    @Override
    public Medico save(Medico medico) {
        repository.save(medico);
        return medico;
    }

    @Override
    public Medico findById(long id) {
        Optional<Medico> medico = repository.findById(id);
        if(medico.isPresent()){
            return medico.get();
        }
        return null;
    }

    @Override
    public Medico delete(long id) {
        Optional<Medico> medico = repository.findById(id);
        if(medico.isPresent()){
            var pac = medico.get();
            repository.delete(pac);
            return pac;
        }
        return null;
    }

    @Override
    public List<Medico> getByName(String nome) {
        return repository.findByNomeIgnoreCaseContaining(nome);
    }
    
}
