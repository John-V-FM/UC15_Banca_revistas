package com.pi.banca.controller;

import com.pi.banca.model.Revista;
import com.pi.banca.model.Venda;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RevistaController {
    
    private List<Revista> listaRevista = new ArrayList<>();
    private List<Venda> listaVenda = new ArrayList<>();
    
    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("error", null);
        return "login";
    }

    @PostMapping("/login")
    public String processarLogin(@RequestParam String username,@RequestParam String password,Model model) {
        
        if ("user".equals(username) && "123".equals(password)){
            return "redirect:/inicio";
        } else {
            model.addAttribute("error", "Usuário ou senha inválidos.");
            return "login";
        }
    }
    
    @GetMapping("/")
    public String inicio() {
        return "redirect:/login"; // Redireciona para a tela de login
    }


    @GetMapping("/inicio")
    public String inicioIndex(){
        return "index";
    }
    
    @GetMapping("/cadastrar")
    public String cadastre(Model model){
        model.addAttribute("revista", new Revista());
        return "cadastro";
    }
    
    @PostMapping("/gravar")
    public String formulario(@ModelAttribute Revista revista, Model model){
        
        if(revista.getId()!= null){
          for(Revista r: listaRevista){
            if(r.getId()== revista.getId()){
               r.setProduto(revista.getProduto());
               r.setQuantidade(revista.getQuantidade());
               r.setValor(revista.getValor());
               break;
            }
          }
        }else{
            revista.setId(listaRevista.size() + 1);
            listaRevista.add(revista);
        }
        return "redirect:/listagem";
    }
    
    @GetMapping("/listagem")
    public String listaForm(Model model){
        model.addAttribute("lista", listaRevista);
        return "listagem";
    }
    
    @GetMapping("/exibir")
    public String exibir(Model model, @RequestParam String id) {
        Integer idRevista = Integer.parseInt(id);

        Revista revistaEncontrada = revistaPorId(idRevista);
        if (revistaEncontrada == null) {
            model.addAttribute("error", "Revista não encontrada.");
            return "redirect:/listagem";
        }

        Venda venda = new Venda();
        venda.setRevista(revistaEncontrada);

        model.addAttribute("revista", revistaEncontrada);
        model.addAttribute("venda", venda);

        return "exibir";
    }
    
    @GetMapping("/alterar-revista")
    public String alterarRevista(Model model, @RequestParam String id){
        
       Integer idRevista = Integer.parseInt(id); 
       model.addAttribute("revista", revistaPorId(idRevista));
       
       return "cadastro";
    }
    
    @GetMapping("/excluir-revista")
    public String excluirRevista(Model model, @RequestParam String id){
        
       Integer idRevista = Integer.parseInt(id);
       
       Revista registroEncontrado = new Revista();
       listaRevista.remove(revistaPorId(idRevista));
       
       return "redirect:/listagem";
    }
    
    @PostMapping("/gravar-venda")
    public String formulario(@ModelAttribute Venda venda, Model model) {
    
        Revista revista = revistaPorId(venda.getRevista().getId());

        if (revista == null || venda.getQtdVenda() <= 0 || venda.getQtdVenda() > revista.getQuantidade()) {
            model.addAttribute("error", "Quantidade de venda inválida.");
            model.addAttribute("revista", revista);
            model.addAttribute("venda", venda);
            return "exibir";
        }

        int novaQuantidade = revista.getQuantidade() - venda.getQtdVenda();
        if (novaQuantidade <= 0) {
            listaRevista.remove(revista);
        } else {
            revista.setQuantidade(novaQuantidade);
        }

        listaVenda.add(venda);

        return "redirect:/listagem";
    }
    
    public Revista revistaPorId(Integer idRevista){
        Revista registroEncontrado = new Revista();
        for(Revista r: listaRevista){
           if(r.getId()== idRevista){
               registroEncontrado = r;
               break;
           }
        }
        return registroEncontrado;
    }
}
