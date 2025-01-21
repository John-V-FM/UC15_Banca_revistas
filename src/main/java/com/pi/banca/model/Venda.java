package com.pi.banca.model;

import lombok.Data;

@Data
public class Venda {
    
    private Integer id;
    private Integer qtdVenda;
    private float total;
    private Revista revista; 
}
