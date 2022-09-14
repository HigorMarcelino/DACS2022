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

import br.univille.apidacs2022.service.ConsultaService;
import br.univille.coredacs2022.entity.Consulta;

@RestController
@RequestMapping("/api/v1/consultas")
public class ConsultaControllerApi {
    @Autowired
    private ConsultaService service;

    @GetMapping
    public ResponseEntity<List<Consulta>> getAll(){
        var listaConsultas = service.getAll();
        return new 
            ResponseEntity<List<Consulta>>
                (listaConsultas,HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Consulta> 
            getById(@PathVariable("id") long id){
        
        var consulta = service.findById(id);
        return new ResponseEntity<Consulta>
                (consulta,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Consulta> 
        insertConsulta(@RequestBody Consulta consulta){
        if(consulta.getId() == 0){
            service.save(consulta);
            return new ResponseEntity<Consulta>(consulta,HttpStatus.CREATED);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Consulta>
            update(@PathVariable("id") long id, 
                   @RequestBody Consulta consulta){
        
        var consultaAntigo = service.findById(id);
        if(consultaAntigo == null){
            return ResponseEntity.notFound().build();
        }

        consultaAntigo.setData(consulta.getData());
        consultaAntigo.setStatus(consulta.getStatus());
        consultaAntigo.setListaProcedimentos(consulta.getListaProcedimentos());

        service.save(consultaAntigo);

        return new ResponseEntity<Consulta>(consultaAntigo,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Consulta>
            delete(@PathVariable("id") long id){
        
        var consultaAntigo = service.findById(id);
        if(consultaAntigo == null){
            return ResponseEntity.notFound().build();
        }
        service.delete(consultaAntigo.getId());

        return new ResponseEntity<Consulta>(consultaAntigo,HttpStatus.OK);
    }
}
