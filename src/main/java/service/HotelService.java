package service;

import model.*;
import repository.*;
import org.springframework.stereotype.Service;
import java.util.List;

import model.Hotel;
import repository.HotelRepository;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public List<Hotel> listarTodos() {
        return hotelRepository.findAll();
    }

    public Hotel buscarPorId(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel não encontrado."));
    }

    public Hotel salvar(Hotel hotel) {
        return hotelRepository.save(hotel);
    }
}