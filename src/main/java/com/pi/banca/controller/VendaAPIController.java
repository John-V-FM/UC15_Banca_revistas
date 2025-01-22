package com.pi.banca.controller;

import com.pi.banca.model.Venda;
import com.pi.banca.service.VendaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/venda")
public class VendaAPIController {
    
    @Autowired
    VendaService vendaService;
    
    @PostMapping("/adicionar")
    public ResponseEntity<Venda> criar(@RequestBody Venda venda){
        
        Venda novaVenda = vendaService.criar(venda);
        return new ResponseEntity<>(novaVenda, HttpStatus.CREATED);
    }
    
    @GetMapping("/pesquisa/{idRevista}")
    public ResponseEntity<List> listarVenda(@PathVariable Integer idRevista){
        List<Venda> lista = vendaService.buscarTodosPeloIdRevista(idRevista);
        return new ResponseEntity<>(lista,HttpStatus.OK);
    }
}
