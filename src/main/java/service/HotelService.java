package service;

import model.Hotel;
import repository.HotelRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public List<Hotel> listarTodos() {
        return hotelRepository.findAll();
    }

    public Optional<Hotel> buscarPorId(Long id) {
        return hotelRepository.findById(id);
    }

    public Hotel salvar(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public Hotel atualizar(Long id, Hotel hotel) {
        if (!hotelRepository.existsById(id)) {
            throw new IllegalArgumentException("Registro não encontrado.");
        }
        hotel.setId(id);
        return hotelRepository.save(hotel);
    }

    public void excluir(Long id) {
        hotelRepository.deleteById(id);
    }
}
