package controller;

import model.Servico;
import service.ServicoService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/servicos")
public class ServicoController {

    private static final String BASE = "/servicos";

    private final ServicoService servicoService;

    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    private void preparar(Model model) {
        model.addAttribute("titulo", "Gerenciamento de Serviços");
        model.addAttribute("tipo", "servico");
        model.addAttribute("urlBase", BASE);
        model.addAttribute("urlSalvar", BASE);
        model.addAttribute("lista", servicoService.listarTodos());
    }

    @GetMapping
    public String listar(Model model) {
        preparar(model);
        return "painel";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes ra) {
        Servico registro = servicoService.buscarPorId(id).orElse(null);
        if (registro == null) {
            ra.addFlashAttribute("erro", "Serviço não encontrado.");
            return "redirect:" + BASE;
        }
        preparar(model);
        model.addAttribute("urlSalvar", BASE + "/" + id);
        model.addAttribute("registro", registro);
        return "painel";
    }

    @PostMapping
    public String salvar(@ModelAttribute Servico servico, RedirectAttributes ra) {
        try {
            servicoService.salvar(servico);
            ra.addFlashAttribute("sucesso", "Serviço salvo com sucesso!");
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:" + BASE;
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute Servico servico, RedirectAttributes ra) {
        try {
            servicoService.atualizar(id, servico);
            ra.addFlashAttribute("sucesso", "Serviço atualizado com sucesso!");
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("erro", e.getMessage());
            return "redirect:" + BASE + "/" + id + "/editar";
        }
        return "redirect:" + BASE;
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        try {
            servicoService.excluir(id);
            ra.addFlashAttribute("sucesso", "Serviço excluído com sucesso!");
        } catch (DataIntegrityViolationException e) {
            ra.addFlashAttribute("erro", "Não é possível excluir: existem registros vinculados a este item.");
        }
        return "redirect:" + BASE;
    }
}
