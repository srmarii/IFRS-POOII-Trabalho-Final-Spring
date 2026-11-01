package service;

import model.*;
import repository.*;
import org.springframework.stereotype.Service;
import java.util.List;

import model.Servico;
import repository.ServicoRepository;

@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;

    public ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    public List<Servico> listarTodos() {
        return servicoRepository.findAll();
    }

    public Servico buscarPorId(Long id) {
        return servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado."));
    }

    public Servico salvar(Servico servico) {
        return servicoRepository.save(servico);
    }
}