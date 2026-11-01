package service;

import model.*;
import repository.*;
import org.springframework.stereotype.Service;
import java.util.List;

import model.Quarto;
import model.StatusQuarto;
import repository.QuartoRepository;

@Service
public class QuartoService {

    private final QuartoRepository quartoRepository;

    public QuartoService(QuartoRepository quartoRepository) {
        this.quartoRepository = quartoRepository;
    }

    public List<Quarto> listarTodos() {
        return quartoRepository.findAll();
    }

    public Quarto buscarPorId(Long id) {
        return quartoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quarto não encontrado."));
    }

    public Quarto salvar(Quarto quarto) {
        if (quarto.getStatusQuarto() == null) {
            quarto.setStatusQuarto(StatusQuarto.DISPONIVEL);
        }
        return quartoRepository.save(quarto);
    }
}