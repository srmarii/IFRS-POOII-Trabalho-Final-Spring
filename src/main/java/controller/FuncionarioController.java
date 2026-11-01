package controller;

import model.Funcionario;
import service.FuncionarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("titulo", "Gerenciamento de Funcionários");
        model.addAttribute("tipo", "funcionario");
        model.addAttribute("urlSalvar", "/funcionarios");
        model.addAttribute("lista", funcionarioService.listarTodos());
        return "painel";
    }

    @PostMapping
    public String salvar(@ModelAttribute Funcionario funcionario) {
        funcionarioService.salvar(funcionario);
        return "redirect:/funcionarios";
    }
}