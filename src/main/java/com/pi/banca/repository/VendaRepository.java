package com.pi.banca.repository;

import com.pi.banca.model.Venda;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Integer>{
    
    List<Venda> findByRevistaId(Integer id);
}
