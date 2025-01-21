package com.pi.banca.model;

import lombok.Data;

@Data
public class Revista {
    
    private Integer id;
    private String produto;
    private Integer quantidade;
    private float valor; 
}
