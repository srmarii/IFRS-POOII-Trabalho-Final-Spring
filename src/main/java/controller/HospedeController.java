package controller;

import model.Hospede;
import service.HospedeService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/hospedes")
public class HospedeController {

    private static final String BASE = "/hospedes";

    private final HospedeService hospedeService;

    public HospedeController(HospedeService hospedeService) {
        this.hospedeService = hospedeService;
    }

    private void preparar(Model model) {
        model.addAttribute("titulo", "Gerenciamento de Hóspedes");
        model.addAttribute("tipo", "hospede");
        model.addAttribute("urlBase", BASE);
        model.addAttribute("urlSalvar", BASE);
        model.addAttribute("lista", hospedeService.listarTodos());
    }

    @GetMapping
    public String listar(Model model) {
        preparar(model);
        return "painel";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes ra) {
        Hospede registro = hospedeService.buscarPorId(id).orElse(null);
        if (registro == null) {
            ra.addFlashAttribute("erro", "Hóspede não encontrado.");
            return "redirect:" + BASE;
        }
        preparar(model);
        model.addAttribute("urlSalvar", BASE + "/" + id);
        model.addAttribute("registro", registro);
        return "painel";
    }

    @PostMapping
    public String salvar(@ModelAttribute Hospede hospede, RedirectAttributes ra) {
        try {
            hospedeService.salvar(hospede);
            ra.addFlashAttribute("sucesso", "Hóspede salvo com sucesso!");
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:" + BASE;
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute Hospede hospede, RedirectAttributes ra) {
        try {
            hospedeService.atualizar(id, hospede);
            ra.addFlashAttribute("sucesso", "Hóspede atualizado com sucesso!");
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("erro", e.getMessage());
            return "redirect:" + BASE + "/" + id + "/editar";
        }
        return "redirect:" + BASE;
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        try {
            hospedeService.excluir(id);
            ra.addFlashAttribute("sucesso", "Hóspede excluído com sucesso!");
        } catch (DataIntegrityViolationException e) {
            ra.addFlashAttribute("erro", "Não é possível excluir: existem registros vinculados a este item.");
        }
        return "redirect:" + BASE;
    }
}
