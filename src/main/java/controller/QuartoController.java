package controller;

import model.Quarto;
import service.HotelService;
import service.QuartoService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/quartos")
public class QuartoController {

    private static final String BASE = "/quartos";

    private final QuartoService quartoService;
    private final HotelService hotelService;

    public QuartoController(QuartoService quartoService, HotelService hotelService) {
        this.quartoService = quartoService;
        this.hotelService = hotelService;
    }

    private void preparar(Model model) {
        model.addAttribute("titulo", "Gerenciamento de Quartos");
        model.addAttribute("tipo", "quarto");
        model.addAttribute("urlBase", BASE);
        model.addAttribute("urlSalvar", BASE);
        model.addAttribute("lista", quartoService.listarTodos());
        model.addAttribute("hoteis", hotelService.listarTodos());
    }

    @GetMapping
    public String listar(Model model) {
        preparar(model);
        return "painel";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes ra) {
        Quarto registro = quartoService.buscarPorId(id).orElse(null);
        if (registro == null) {
            ra.addFlashAttribute("erro", "Quarto não encontrado.");
            return "redirect:" + BASE;
        }
        preparar(model);
        model.addAttribute("urlSalvar", BASE + "/" + id);
        model.addAttribute("registro", registro);
        return "painel";
    }

    @PostMapping
    public String salvar(@RequestParam(required = false) Integer numero,
                         @RequestParam(required = false) Integer capacidade,
                         @RequestParam(required = false) Double valorDiariaBase,
                         @RequestParam(required = false) String tipoQuarto,
                         @RequestParam(required = false) Long hotelId,
                         RedirectAttributes ra) {
        try {
            quartoService.salvar(numero, capacidade, valorDiariaBase, tipoQuarto, hotelId);
            ra.addFlashAttribute("sucesso", "Quarto salvo com sucesso!");
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:" + BASE;
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id,
                            @RequestParam(required = false) Integer numero,
                            @RequestParam(required = false) Integer capacidade,
                            @RequestParam(required = false) Double valorDiariaBase,
                            @RequestParam(required = false) String tipoQuarto,
                            @RequestParam(required = false) Long hotelId,
                            RedirectAttributes ra) {
        try {
            quartoService.atualizar(id, numero, capacidade, valorDiariaBase, tipoQuarto, hotelId);
            ra.addFlashAttribute("sucesso", "Quarto atualizado!");
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("erro", e.getMessage());
            return "redirect:" + BASE + "/" + id + "/editar";
        }
        return "redirect:" + BASE;
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        try {
            quartoService.excluir(id);
            ra.addFlashAttribute("sucesso", "Quarto excluído com sucesso!");
        } catch (DataIntegrityViolationException e) {
            ra.addFlashAttribute("erro", "Não é possível excluir: existem reservas vinculadas a este quarto.");
        }
        return "redirect:" + BASE;
    }
}
