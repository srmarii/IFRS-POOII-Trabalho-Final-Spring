package service;

import model.Funcionario;
import model.Hotel;
import repository.FuncionarioRepository;
import repository.HotelRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final HotelRepository hotelRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository, HotelRepository hotelRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.hotelRepository = hotelRepository;
    }

    public List<Funcionario> listarTodos() {
        return funcionarioRepository.findAll();
    }

    public Optional<Funcionario> buscarPorId(Long id) {
        return funcionarioRepository.findById(id);
    }

    public Funcionario salvar(Funcionario funcionario) {
        funcionario.setHotel(validarHotel(funcionario));
        return funcionarioRepository.save(funcionario);
    }

    public Funcionario atualizar(Long id, Funcionario funcionario) {
        if (!funcionarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Funcionário não encontrado.");
        }
        funcionario.setId(id);
        funcionario.setHotel(validarHotel(funcionario));
        return funcionarioRepository.save(funcionario);
    }

    public void excluir(Long id) {
        funcionarioRepository.deleteById(id);
    }

    private Hotel validarHotel(Funcionario f) {
        if (f.getHotel() == null || f.getHotel().getId() == null) {
            throw new IllegalArgumentException("Selecione um hotel.");
        }
        return hotelRepository.findById(f.getHotel().getId())
                .orElseThrow(() -> new IllegalArgumentException("Hotel não encontrado."));
    }
}
