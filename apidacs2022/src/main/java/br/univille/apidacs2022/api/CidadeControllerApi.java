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

import br.univille.apidacs2022.service.CidadeService;
import br.univille.coredacs2022.entity.Cidade;

@RestController
@RequestMapping("/api/v1/cidades")
public class CidadeControllerApi {
    @Autowired
    private CidadeService service;

    @GetMapping
    public ResponseEntity<List<Cidade>> getAll(){
        var listaCidades = service.getAll();
        return new 
            ResponseEntity<List<Cidade>>
                (listaCidades,HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Cidade> 
            getById(@PathVariable("id") long id){
        
        var Cidade = service.findById(id);
        return new ResponseEntity<Cidade>
                (Cidade,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Cidade> 
        insertCidade(@RequestBody Cidade Cidade){
        if(Cidade.getId() == 0){
            service.save(Cidade);
            return new ResponseEntity<Cidade>(Cidade,HttpStatus.CREATED);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Cidade>> 
                    getByNome(@PathVariable("nome") String nome){
        var listaCidades = service.getByName(nome);
        return new 
            ResponseEntity<List<Cidade>>
                (listaCidades,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cidade>
            update(@PathVariable("id") long id, 
                   @RequestBody Cidade Cidade){
        
        var CidadeAntigo = service.findById(id);
        if(CidadeAntigo == null){
            return ResponseEntity.notFound().build();
        }

        CidadeAntigo.setNome(Cidade.getNome());

        service.save(CidadeAntigo);

        return new ResponseEntity<Cidade>(CidadeAntigo,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cidade>
            delete(@PathVariable("id") long id){
        
        var CidadeAntigo = service.findById(id);
        if(CidadeAntigo == null){
            return ResponseEntity.notFound().build();
        }
        service.delete(CidadeAntigo.getId());

        return new ResponseEntity<Cidade>(HttpStatus.OK);
    }
}
