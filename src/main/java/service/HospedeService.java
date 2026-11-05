package service;

import model.Hospede;
import repository.HospedeRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class HospedeService {

    private final HospedeRepository hospedeRepository;

    public HospedeService(HospedeRepository hospedeRepository) {
        this.hospedeRepository = hospedeRepository;
    }

    public List<Hospede> listarTodos() {
        return hospedeRepository.findAll();
    }

    public Optional<Hospede> buscarPorId(Long id) {
        return hospedeRepository.findById(id);
    }

    public Hospede salvar(Hospede hospede) {
        return hospedeRepository.save(hospede);
    }

    public Hospede atualizar(Long id, Hospede hospede) {
        if (!hospedeRepository.existsById(id)) {
            throw new IllegalArgumentException("Registro não encontrado.");
        }
        hospede.setId(id);
        return hospedeRepository.save(hospede);
    }

    public void excluir(Long id) {
        hospedeRepository.deleteById(id);
    }
}
