package com.pi.banca.controller;

import com.pi.banca.model.Revista;
import com.pi.banca.service.RevistaService;
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

@RestController
@RequestMapping("/revista")
public class RevistaAPIController {
    
    @Autowired
    RevistaService revistaService;
    
    @PostMapping("/adicionar")
    public ResponseEntity<Revista> criar(@RequestBody Revista revista){
        
        Revista novaRevista = revistaService.criar(revista);
        return new ResponseEntity<>(novaRevista, HttpStatus.CREATED);
    }
    
    @GetMapping("/listarTodos")
    public ResponseEntity<List> listar(){
        List<Revista> listarTodasRevista = revistaService.buscarTodos();
        return new ResponseEntity<>(listarTodasRevista,HttpStatus.OK );
    }
    
    @GetMapping("/buscar/{id}")
    public ResponseEntity<Revista> buscar(@PathVariable Integer id){
        Revista revistaEncontrado = revistaService.buscarPorId(id);
        return new ResponseEntity<>(revistaEncontrado, HttpStatus.OK);
    }
    
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Revista> atualizar(@PathVariable Integer id, @RequestBody Revista revista){
        Revista revistaAtualizada = revistaService.atualizar(id, revista);
        return new ResponseEntity<>(revistaAtualizada, HttpStatus.OK);
    }
    
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Revista> excluir(@PathVariable Integer id){
        revistaService.excluir(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
