package service;

import model.Hospede;
import repository.HospedeRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HospedeService {

    private final HospedeRepository hospedeRepository;

    public HospedeService(HospedeRepository hospedeRepository) {
        this.hospedeRepository = hospedeRepository;
    }

    public List<Hospede> listarTodos() {
        return hospedeRepository.findAll();
    }

    public Hospede salvar(Hospede hospede) {
        return hospedeRepository.save(hospede);
    }
}