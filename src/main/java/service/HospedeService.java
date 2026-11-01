package service;

import model.*;
import repository.*;
import org.springframework.stereotype.Service;
import java.util.List;

import model.Hospede;
import repository.HospedeRepository;

@Service
public class HospedeService {

    private final HospedeRepository hospedeRepository;

    public HospedeService(HospedeRepository hospedeRepository) {
        this.hospedeRepository = hospedeRepository;
    }

    public List<Hospede> listarTodos() {
        return hospedeRepository.findAll();
    }

    public Hospede buscarPorId(Long id) {
        return hospedeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hóspede não encontrado."));
    }

    public Hospede salvar(Hospede hospede) {
        return hospedeRepository.save(hospede);
    }
}