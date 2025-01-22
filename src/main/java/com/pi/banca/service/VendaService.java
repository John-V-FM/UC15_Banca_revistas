package com.pi.banca.service;

import com.pi.banca.model.Venda;
import com.pi.banca.repository.VendaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendaService {
    
    @Autowired
    VendaRepository vendaRepository;
    
    public Venda buscarPorId(Integer id){
        return vendaRepository.findById(id).orElseThrow();
    }
    
    public Venda criar(Venda v){
        v.setId(null);
        vendaRepository.save(v);
        return v;
    }
    
    public void excluir(Integer id){
        Venda vendaEncontrada = buscarPorId(id);
        vendaRepository.deleteById(vendaEncontrada.getId());
    }
    
    public List<Venda> buscarTodosPeloIdRevista(Integer idRevista){
        return vendaRepository.findByRevistaId(idRevista);
    }
}
