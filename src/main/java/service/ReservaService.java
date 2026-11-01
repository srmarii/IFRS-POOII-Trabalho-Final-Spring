package service;

import model.*;
import repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.temporal.ChronoUnit;
import java.util.List;

import model.Hospede;
import model.Quarto;
import model.Reserva;
import model.Servico;
import model.StatusQuarto;
import model.StatusReserva;
import repository.ReservaRepository;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final QuartoService quartoService;
    private final HospedeService hospedeService;
    private final ServicoService servicoService;

    public ReservaService(ReservaRepository reservaRepository, QuartoService quartoService,
                          HospedeService hospedeService, ServicoService servicoService) {
        this.reservaRepository = reservaRepository;
        this.quartoService = quartoService;
        this.hospedeService = hospedeService;
        this.servicoService = servicoService;
    }

    public List<Reserva> listarTodas() {
        return reservaRepository.findAll();
    }

    public Reserva buscarPorId(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada."));
    }

    @Transactional
    public Reserva criarReserva(Reserva reserva) {
        Quarto quarto = quartoService.buscarPorId(reserva.getQuarto().getId());
        Hospede hospede = hospedeService.buscarPorId(reserva.getHospede().getId());

        if (reserva.getDataSaida().isBefore(reserva.getDataEntrada())) {
            throw new IllegalArgumentException("A data de saída deve ser posterior à data de entrada.");
        }

        reserva.setQuarto(quarto);
        reserva.setHospede(hospede);
        reserva.setStatusReserva(StatusReserva.PENDENTE);
        
        calcularValorTotal(reserva);

        return reservaRepository.save(reserva);
    }

    @Transactional
    public void realizarCheckIn(Long reservaId) {
        Reserva reserva = buscarPorId(reservaId);
        Quarto quarto = reserva.getQuarto();

        reserva.setStatusReserva(StatusReserva.CONFIRMADA);
        quarto.setStatusQuarto(StatusQuarto.OCUPADO);

        quartoService.salvar(quarto);
        reservaRepository.save(reserva);
    }

    @Transactional
    public void realizarCheckOut(Long reservaId) {
        Reserva reserva = buscarPorId(reservaId);
        Quarto quarto = reserva.getQuarto();

        reserva.setStatusReserva(StatusReserva.FINALIZADA);
        quarto.setStatusQuarto(StatusQuarto.DISPONIVEL);

        calcularValorTotal(reserva);

        quartoService.salvar(quarto);
        reservaRepository.save(reserva);
    }

    @Transactional
    public void adicionarServico(Long reservaId, Long servicoId) {
        Reserva reserva = buscarPorId(reservaId);
        Servico servico = servicoService.buscarPorId(servicoId);

        reserva.getServicos().add(servico);
        calcularValorTotal(reserva);

        reservaRepository.save(reserva);
    }

    private void calcularValorTotal(Reserva reserva) {
        long dias = ChronoUnit.DAYS.between(reserva.getDataEntrada(), reserva.getDataSaida());
        if (dias <= 0) {
            dias = 1; // Mínimo de 1 diária
        }

        double valorDiarias = dias * reserva.getQuarto().getValorDiaria();
        double valorServicos = reserva.getServicos().stream()
                .mapToDouble(Servico::getValor)
                .sum();

        reserva.setValorTotal(valorDiarias + valorServicos);
    }
}