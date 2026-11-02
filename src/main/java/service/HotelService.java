package service;

import model.Hotel;
import repository.HotelRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public List<Hotel> listarTodos() {
        return hotelRepository.findAll();
    }

    public Hotel salvar(Hotel hotel) {
        return hotelRepository.save(hotel);
    }
}