package com.pi.banca.service;

import com.pi.banca.model.Revista;
import com.pi.banca.repository.RevistaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RevistaService {
    
    @Autowired
    RevistaRepository revistaRepository;
    
    public Revista criar(Revista revista){
        
        revista.setId(null);
        revistaRepository.save(revista);
        return revista;
    }
    
    public List<Revista> buscarTodos(){
        return revistaRepository.findAll();
    }
    
    public void excluir(Integer id){
        Revista revistaEncontrado = buscarPorId(id);
        revistaRepository.deleteById(revistaEncontrado.getId());
    }
    
    public Revista buscarPorId(Integer id){
         return revistaRepository.findById(id).orElseThrow();
    }
    
    public Revista atualizar(Integer id, Revista revista){
        Revista revistaEncontrado = buscarPorId(id);
        
        revistaEncontrado.setProduto(revista.getProduto());
        revistaEncontrado.setQuantidade(revista.getQuantidade());
        revistaEncontrado.setValor(revista.getValor());
        revistaRepository.save(revistaEncontrado);
        return revistaEncontrado;
    }
}
