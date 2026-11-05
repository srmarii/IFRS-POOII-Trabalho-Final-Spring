package controller;

import model.Funcionario;
import service.FuncionarioService;
import service.HotelService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioController {

    private static final String BASE = "/funcionarios";

    private final FuncionarioService funcionarioService;
    private final HotelService hotelService;

    public FuncionarioController(FuncionarioService funcionarioService, HotelService hotelService) {
        this.funcionarioService = funcionarioService;
        this.hotelService = hotelService;
    }

    private void preparar(Model model) {
        model.addAttribute("titulo", "Gerenciamento de Funcionários");
        model.addAttribute("tipo", "funcionario");
        model.addAttribute("urlBase", BASE);
        model.addAttribute("urlSalvar", BASE);
        model.addAttribute("lista", funcionarioService.listarTodos());
        model.addAttribute("hoteis", hotelService.listarTodos());
    }

    @GetMapping
    public String listar(Model model) {
        preparar(model);
        return "painel";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes ra) {
        Funcionario registro = funcionarioService.buscarPorId(id).orElse(null);
        if (registro == null) {
            ra.addFlashAttribute("erro", "Funcionário não encontrado.");
            return "redirect:" + BASE;
        }
        preparar(model);
        model.addAttribute("urlSalvar", BASE + "/" + id);
        model.addAttribute("registro", registro);
        return "painel";
    }

    @PostMapping
    public String salvar(@ModelAttribute Funcionario funcionario, RedirectAttributes ra) {
        try {
            funcionarioService.salvar(funcionario);
            ra.addFlashAttribute("sucesso", "Funcionário salvo com sucesso!");
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:" + BASE;
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute Funcionario funcionario, RedirectAttributes ra) {
        try {
            funcionarioService.atualizar(id, funcionario);
            ra.addFlashAttribute("sucesso", "Funcionário atualizado com sucesso!");
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("erro", e.getMessage());
            return "redirect:" + BASE + "/" + id + "/editar";
        }
        return "redirect:" + BASE;
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        try {
            funcionarioService.excluir(id);
            ra.addFlashAttribute("sucesso", "Funcionário excluído com sucesso!");
        } catch (DataIntegrityViolationException e) {
            ra.addFlashAttribute("erro", "Não é possível excluir: existem registros vinculados a este item.");
        }
        return "redirect:" + BASE;
    }
}
