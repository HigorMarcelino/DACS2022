package br.univille.apidacs2022.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.univille.apidacs2022.service.MedicoService;
import br.univille.coredacs2022.entity.Medico;

@RestController
@RequestMapping("/api/v1/medicos")
public class MedicoControllerApi {
    
    @Autowired
    private MedicoService service;

    @GetMapping
    public ResponseEntity<List<Medico>> getAll(){
        var listaMedicos = service.getAll();
        return new 
            ResponseEntity<List<Medico>>
                (listaMedicos,HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Medico> 
            getById(@PathVariable("id") long id){
        
        var medico = service.findById(id);
        return new ResponseEntity<Medico>
                (medico,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Medico> 
        insertMedico(@RequestBody Medico medico){
        if(medico.getId() == 0){
            service.save(medico);
            return new ResponseEntity<Medico>(medico,HttpStatus.CREATED);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Medico>> 
                    getByNome(@PathVariable("nome") String nome){
        var listaMedicos = service.getByName(nome);
        return new 
            ResponseEntity<List<Medico>>
                (listaMedicos,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medico>
            update(@PathVariable("id") long id, 
                   @RequestBody Medico medico){
        
        var medicoAntigo = service.findById(id);
        if(medicoAntigo == null){
            return ResponseEntity.notFound().build();
        }

        medicoAntigo.setNome(medico.getNome());
        medicoAntigo.setCRM(medico.getCRM());
        
        service.save(medicoAntigo);

        return new ResponseEntity<Medico>(medicoAntigo,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Medico>
            delete(@PathVariable("id") long id){
        
        var MedicoAntigo = service.findById(id);
        if(MedicoAntigo == null){
            return ResponseEntity.notFound().build();
        }
        service.delete(MedicoAntigo.getId());

        return new ResponseEntity<Medico>(HttpStatus.OK);
    }
    
}
