package controller;

import model.Reserva;
import service.HospedeService;
import service.QuartoService;
import service.ReservaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Locale;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    private static final String BASE = "/reservas";

    private final ReservaService reservaService;
    private final HospedeService hospedeService;
    private final QuartoService quartoService;

    public ReservaController(ReservaService reservaService,
                             HospedeService hospedeService,
                             QuartoService quartoService) {
        this.reservaService = reservaService;
        this.hospedeService = hospedeService;
        this.quartoService = quartoService;
    }

    private void preparar(Model model) {
        model.addAttribute("titulo", "Gerenciamento de Reservas");
        model.addAttribute("tipo", "reserva");
        model.addAttribute("urlBase", BASE);
        model.addAttribute("urlSalvar", BASE);
        model.addAttribute("lista", reservaService.listarTodas());
        model.addAttribute("hospedes", hospedeService.listarTodos());
        model.addAttribute("quartos", quartoService.listarTodos());
        model.addAttribute("situacoes", ReservaService.SITUACOES);
    }

    @GetMapping
    public String listar(Model model) {
        preparar(model);
        return "painel";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes ra) {
        Reserva registro = reservaService.buscarPorId(id).orElse(null);
        if (registro == null) {
            ra.addFlashAttribute("erro", "Reserva não encontrada.");
            return "redirect:" + BASE;
        }
        preparar(model);
        model.addAttribute("urlSalvar", BASE + "/" + id);
        model.addAttribute("registro", registro);
        return "painel";
    }

    // Quarto é abstrata, então o Spring não consegue montar "quarto.id" via @ModelAttribute.
    // Por isso lemos os ids como parâmetros simples e o service busca as entidades.
    @PostMapping
    public String salvar(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataEntrada,
                         @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataSaida,
                         @RequestParam(required = false) Long hospedeId,
                         @RequestParam(required = false) Long quartoId,
                         RedirectAttributes ra) {
        try {
            Reserva salva = reservaService.salvar(dataEntrada, dataSaida, hospedeId, quartoId);
            ra.addFlashAttribute("sucesso", "Reserva salva! Valor Total: R$ "
                    + String.format(Locale.forLanguageTag("pt-BR"), "%.2f", salva.getValorTotal()));
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:" + BASE;
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id,
                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataEntrada,
                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataSaida,
                            @RequestParam(required = false) String situacao,
                            @RequestParam(required = false) Long hospedeId,
                            @RequestParam(required = false) Long quartoId,
                            RedirectAttributes ra) {
        try {
            reservaService.atualizar(id, dataEntrada, dataSaida, situacao, hospedeId, quartoId);
            ra.addFlashAttribute("sucesso", "Reserva atualizada com sucesso!");
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("erro", e.getMessage());
            return "redirect:" + BASE + "/" + id + "/editar";
        }
        return "redirect:" + BASE;
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        reservaService.excluir(id);
        ra.addFlashAttribute("sucesso", "Reserva eliminada com sucesso!");
        return "redirect:" + BASE;
    }
}
