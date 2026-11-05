package controller;

import model.Hotel;
import service.HotelService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/hoteis")
public class HotelController {

    private static final String BASE = "/hoteis";

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    private void preparar(Model model) {
        model.addAttribute("titulo", "Gerenciamento de Hotéis");
        model.addAttribute("tipo", "hotel");
        model.addAttribute("urlBase", BASE);
        model.addAttribute("urlSalvar", BASE);
        model.addAttribute("lista", hotelService.listarTodos());
    }

    @GetMapping
    public String listar(Model model) {
        preparar(model);
        return "painel";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes ra) {
        Hotel registro = hotelService.buscarPorId(id).orElse(null);
        if (registro == null) {
            ra.addFlashAttribute("erro", "Hotel não encontrado.");
            return "redirect:" + BASE;
        }
        preparar(model);
        model.addAttribute("urlSalvar", BASE + "/" + id);
        model.addAttribute("registro", registro);
        return "painel";
    }

    @PostMapping
    public String salvar(@ModelAttribute Hotel hotel, RedirectAttributes ra) {
        try {
            hotelService.salvar(hotel);
            ra.addFlashAttribute("sucesso", "Hotel salvo com sucesso!");
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:" + BASE;
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute Hotel hotel, RedirectAttributes ra) {
        try {
            hotelService.atualizar(id, hotel);
            ra.addFlashAttribute("sucesso", "Hotel atualizado com sucesso!");
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("erro", e.getMessage());
            return "redirect:" + BASE + "/" + id + "/editar";
        }
        return "redirect:" + BASE;
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        try {
            hotelService.excluir(id);
            ra.addFlashAttribute("sucesso", "Hotel excluído com sucesso!");
        } catch (DataIntegrityViolationException e) {
            ra.addFlashAttribute("erro", "Não é possível excluir: existem registros vinculados a este item.");
        }
        return "redirect:" + BASE;
    }
}
