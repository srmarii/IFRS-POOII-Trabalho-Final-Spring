package controller;

import model.Servico;
import service.ServicoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/servicos")
public class ServicoController {

    private final ServicoService servicoService;

    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("titulo", "Gerenciamento de Serviços Extra");
        model.addAttribute("tipo", "servico");
        model.addAttribute("urlSalvar", "/servicos");
        model.addAttribute("lista", servicoService.listarTodos());
        return "painel";
    }

    @PostMapping
    public String salvar(@ModelAttribute Servico servico) {
        servicoService.salvar(servico);
        return "redirect:/servicos";
    }
}