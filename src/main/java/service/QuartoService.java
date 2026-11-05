package service;

import model.Hotel;
import model.Quarto;
import model.QuartoSimples;
import model.QuartoVIP;
import repository.HotelRepository;
import repository.QuartoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class QuartoService {

    private final QuartoRepository quartoRepository;
    private final HotelRepository hotelRepository;

    public QuartoService(QuartoRepository quartoRepository, HotelRepository hotelRepository) {
        this.quartoRepository = quartoRepository;
        this.hotelRepository = hotelRepository;
    }

    public List<Quarto> listarTodos() {
        return quartoRepository.findAll();
    }

    public Optional<Quarto> buscarPorId(Long id) {
        return quartoRepository.findById(id);
    }

    public Quarto salvar(Integer numero, Integer capacidade, Double valorBase, String tipo, Long hotelId) {
        Hotel hotel = validar(numero, capacidade, valorBase, hotelId);

        Quarto quarto = "VIP".equalsIgnoreCase(tipo) ? new QuartoVIP() : new QuartoSimples();
        quarto.setNumero(numero);
        quarto.setCapacidade(capacidade);
        quarto.setValorDiariaBase(valorBase);
        quarto.setHotel(hotel);
        return quartoRepository.save(quarto);
    }

    @Transactional
    public void atualizar(Long id, Integer numero, Integer capacidade, Double valorBase, String tipo, Long hotelId) {
        if (!quartoRepository.existsById(id)) {
            throw new IllegalArgumentException("Quarto não encontrado.");
        }
        validar(numero, capacidade, valorBase, hotelId);
        String tipoNormalizado = "VIP".equalsIgnoreCase(tipo) ? "VIP" : "Simples";
        quartoRepository.atualizar(id, numero, capacidade, valorBase, hotelId, tipoNormalizado);
    }

    public void excluir(Long id) {
        quartoRepository.deleteById(id);
    }

    private Hotel validar(Integer numero, Integer capacidade, Double valorBase, Long hotelId) {
        if (numero == null || capacidade == null || valorBase == null || hotelId == null) {
            throw new IllegalArgumentException("Preencha número, capacidade, valor da diária e hotel.");
        }
        return hotelRepository.findById(hotelId)
                .orElseThrow(() -> new IllegalArgumentException("Hotel não encontrado."));
    }
}
