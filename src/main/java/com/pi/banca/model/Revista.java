package com.pi.banca.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Revista")
public class Revista {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String produto;
    private Integer quantidade;
    private float valor;
    
    @OneToMany(mappedBy = "revista", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Venda> vendas;
}
