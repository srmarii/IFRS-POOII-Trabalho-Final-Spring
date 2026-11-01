package controller;

import model.Quarto;
import service.QuartoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/quartos")
public class QuartoController {

    private final QuartoService quartoService;

    public QuartoController(QuartoService quartoService) {
        this.quartoService = quartoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("titulo", "Gerenciamento de Quartos");
        model.addAttribute("tipo", "quarto");
        model.addAttribute("urlSalvar", "/quartos");
        model.addAttribute("lista", quartoService.listarTodos());
        return "painel";
    }

    @PostMapping
    public String salvar(@ModelAttribute Quarto quarto) {
        quartoService.salvar(quarto);
        return "redirect:/quartos";
    }
}