package service;

import model.Hospede;
import model.Quarto;
import model.Reserva;
import repository.HospedeRepository;
import repository.QuartoRepository;
import repository.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    public static final List<String> SITUACOES =
            List.of(Reserva.PENDENTE, Reserva.CONFIRMADA, Reserva.CANCELADA, Reserva.FINALIZADA);

    private final ReservaRepository reservaRepository;
    private final HospedeRepository hospedeRepository;
    private final QuartoRepository quartoRepository;

    public ReservaService(ReservaRepository reservaRepository,
                          HospedeRepository hospedeRepository,
                          QuartoRepository quartoRepository) {
        this.reservaRepository = reservaRepository;
        this.hospedeRepository = hospedeRepository;
        this.quartoRepository = quartoRepository;
    }

    public List<Reserva> listarTodas() {
        return reservaRepository.findAll();
    }

    public Optional<Reserva> buscarPorId(Long id) {
        return reservaRepository.findById(id);
    }

    @Transactional
    public Reserva salvar(LocalDate entrada, LocalDate saida, Long hospedeId, Long quartoId) {
        Hospede hospede = buscarHospede(hospedeId);
        Quarto quarto = buscarQuarto(quartoId);
        validarDatas(entrada, saida);

        if (!disponivel(quarto.getId(), entrada, saida, null)) {
            throw new IllegalArgumentException("Quarto indisponível neste período!");
        }

        Reserva reserva = new Reserva();
        reserva.setDataEntrada(entrada);
        reserva.setDataSaida(saida);
        reserva.setSituacao(Reserva.PENDENTE);
        reserva.setHospede(hospede);
        reserva.setQuarto(quarto);
        reserva.setValorTotal(calcularTotal(entrada, saida, quarto));
        return reservaRepository.save(reserva);
    }

    @Transactional
    public Reserva atualizar(Long id, LocalDate entrada, LocalDate saida, String situacao,
                             Long hospedeId, Long quartoId) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reserva não encontrada."));

        Hospede hospede = buscarHospede(hospedeId);
        Quarto quarto = buscarQuarto(quartoId);
        validarDatas(entrada, saida);

        String novaSituacao = SITUACOES.contains(situacao) ? situacao : Reserva.PENDENTE;

        if (!Reserva.CANCELADA.equalsIgnoreCase(novaSituacao)
                && !disponivel(quarto.getId(), entrada, saida, id)) {
            throw new IllegalArgumentException("O quarto está ocupado neste período por outra reserva!");
        }

        reserva.setDataEntrada(entrada);
        reserva.setDataSaida(saida);
        reserva.setSituacao(novaSituacao);
        reserva.setHospede(hospede);
        reserva.setQuarto(quarto);
        reserva.setValorTotal(calcularTotal(entrada, saida, quarto));
        return reservaRepository.save(reserva);
    }

    public void excluir(Long id) {
        reservaRepository.deleteById(id);
    }

    private Hospede buscarHospede(Long hospedeId) {
        if (hospedeId == null) {
            throw new IllegalArgumentException("Selecione um hóspede.");
        }
        return hospedeRepository.findById(hospedeId)
                .orElseThrow(() -> new IllegalArgumentException("Hóspede não encontrado."));
    }

    private Quarto buscarQuarto(Long quartoId) {
        if (quartoId == null) {
            throw new IllegalArgumentException("Selecione um quarto.");
        }
        return quartoRepository.findById(quartoId)
                .orElseThrow(() -> new IllegalArgumentException("Quarto não encontrado."));
    }

    private void validarDatas(LocalDate entrada, LocalDate saida) {
        if (entrada == null || saida == null) {
            throw new IllegalArgumentException("Informe as datas de entrada e saída.");
        }
        if (!saida.isAfter(entrada)) {
            throw new IllegalArgumentException("A data de saída deve ser maior que a de entrada.");
        }
    }

    private boolean disponivel(Long quartoId, LocalDate entrada, LocalDate saida, Long ignorarId) {
        long conflitos = reservaRepository.contarConflitos(
                quartoId, entrada, saida, ignorarId == null ? -1L : ignorarId);
        return conflitos == 0;
    }

    private double calcularTotal(LocalDate entrada, LocalDate saida, Quarto quarto) {
        long dias = ChronoUnit.DAYS.between(entrada, saida);
        return dias * quarto.calcularValorDiaria();
    }
}
