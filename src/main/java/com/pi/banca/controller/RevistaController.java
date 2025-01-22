package com.pi.banca.controller;

import com.pi.banca.model.Preferencia;
import com.pi.banca.model.Revista;
import com.pi.banca.model.Venda;
import com.pi.banca.service.RevistaService;
import com.pi.banca.service.VendaService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RevistaController {
    
    @Autowired
    RevistaService revistaService;
    
    @Autowired
    VendaService vendaService;
    
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
    
    @PostMapping("/preferencias")
    public ModelAndView gravarPreferencias(@ModelAttribute Preferencia pref,HttpServletResponse response){
        Cookie cookiePrefEstilo = new Cookie("pref-estilo",pref.getEstilo());
        cookiePrefEstilo.setDomain("localhost");
        cookiePrefEstilo.setHttpOnly(true);
        cookiePrefEstilo.setMaxAge(86400);
        response.addCookie(cookiePrefEstilo);
        return new ModelAndView("redirect:/inicio");
    }
    
    @GetMapping("/")
    public String inicio() {
        return "redirect:/login"; // Redireciona para a tela de login
    }


    @GetMapping("/inicio")
    public String inicioIndex(@CookieValue(name="pref-estilo", defaultValue="claro")String tema, Model model){
        model.addAttribute("css", tema);
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
            revistaService.atualizar(revista.getId(), revista);
        }else{
            revistaService.criar(revista);
        }
        return "redirect:/listagem";
    }
    
    @GetMapping("/listagem")
    public String listaForm(Model model){
        model.addAttribute("lista", revistaService.buscarTodos());
        return "listagem";
    }
    
    @GetMapping("/exibir")
    public String exibir(Model model, @RequestParam String id) {
        try {
            Integer idRevista = Integer.parseInt(id);

            // Busca a revista pelo ID
            Revista revistaEncontrada = revistaService.buscarPorId(idRevista);

            Venda venda = new Venda();
            venda.setRevista(revistaEncontrada);

            model.addAttribute("revista", revistaEncontrada);
            model.addAttribute("venda", venda);

            return "exibir";
        } catch (NumberFormatException e) {
            model.addAttribute("error", "ID inválido.");
            return "redirect:/listagem";
        } catch (RuntimeException e) {
            model.addAttribute("error", "Revista não encontrada.");
            return "redirect:/listagem";
        }
    }
    
    @GetMapping("/alterar-revista")
    public String alterarRevista(Model model, @RequestParam String id){
        
       Integer idRevista = Integer.parseInt(id); 
       model.addAttribute("revista", revistaService.buscarPorId(idRevista));
       return "cadastro";
    }
    
    @GetMapping("/excluir-revista")
    public String excluirRevista(Model model, @RequestParam String id){
        
       Integer idRevista = Integer.parseInt(id);
       revistaService.excluir(idRevista);
       return "redirect:/listagem";
    }
    
    @PostMapping("/gravar-venda")
    public String formularioVenda(@ModelAttribute Venda venda, Model model) {
        try {
            // Busca a revista no banco de dados
            Revista revista = revistaService.buscarPorId(venda.getRevista().getId());

            // Validação da quantidade de venda
            if (venda.getQtdVenda() <= 0 || venda.getQtdVenda() > revista.getQuantidade()) {
                model.addAttribute("error", "Quantidade de venda inválida.");
                model.addAttribute("revista", revista);
                model.addAttribute("venda", venda);
                return "exibir";
            }

            // Atualiza o estoque da revista
            int novaQuantidade = revista.getQuantidade() - venda.getQtdVenda();
            revista.setQuantidade(novaQuantidade);

            // Remove a revista da lista se a quantidade chegar a zero
            if (novaQuantidade == 0) {
                revistaService.excluir(revista.getId());
            } else {
                revistaService.atualizar(revista.getId(), revista);
            }

            // Calcula o valor total da venda
            float total = venda.getQtdVenda() * revista.getValor();
            venda.setTotal(total);

            // Salva a venda no banco de dados
            vendaService.criar(venda);

            return "redirect:/listagem";
        } catch (RuntimeException e) {
            model.addAttribute("error", "Erro ao processar a venda.");
            return "redirect:/listagem";
        }
    }
}
