package service;

import model.Servico;
import repository.ServicoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;

    public ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    public List<Servico> listarTodos() {
        return servicoRepository.findAll();
    }

    public Optional<Servico> buscarPorId(Long id) {
        return servicoRepository.findById(id);
    }

    public Servico salvar(Servico servico) {
        return servicoRepository.save(servico);
    }

    public Servico atualizar(Long id, Servico servico) {
        if (!servicoRepository.existsById(id)) {
            throw new IllegalArgumentException("Registro não encontrado.");
        }
        servico.setId(id);
        return servicoRepository.save(servico);
    }

    public void excluir(Long id) {
        servicoRepository.deleteById(id);
    }
}
