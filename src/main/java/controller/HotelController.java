package controller;

import model.Hotel;
import service.HotelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/hoteis")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("titulo", "Gerenciamento de Hotéis");
        model.addAttribute("tipo", "hotel");
        model.addAttribute("urlSalvar", "/hoteis");
        model.addAttribute("lista", hotelService.listarTodos());
        return "painel";
    }

    @PostMapping
    public String salvar(@ModelAttribute Hotel hotel) {
        hotelService.salvar(hotel);
        return "redirect:/hoteis";
    }
}