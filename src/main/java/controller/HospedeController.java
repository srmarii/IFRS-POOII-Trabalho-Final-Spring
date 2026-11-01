package controller;

import model.Hospede;
import service.HospedeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/hospedes")
public class HospedeController {

    private final HospedeService hospedeService;

    public HospedeController(HospedeService hospedeService) {
        this.hospedeService = hospedeService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("titulo", "Gerenciamento de Hóspedes");
        model.addAttribute("tipo", "hospede");
        model.addAttribute("urlSalvar", "/hospedes");
        model.addAttribute("lista", hospedeService.listarTodos());
        return "painel";
    }

    @PostMapping
    public String salvar(@ModelAttribute Hospede hospede) {
        hospedeService.salvar(hospede);
        return "redirect:/hospedes";
    }
}