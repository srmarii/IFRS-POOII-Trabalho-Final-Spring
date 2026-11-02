package controller;

import model.Reserva;
import service.ReservaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("titulo", "Gerenciamento de Reservas");
        model.addAttribute("tipo", "reserva");
        model.addAttribute("urlSalvar", "/reservas");
        model.addAttribute("lista", reservaService.listarTodas());
        return "painel";
    }

    @PostMapping
    public String salvar(@ModelAttribute Reserva reserva) {
        reservaService.salvar(reserva);
        return "redirect:/reservas";
    }
}