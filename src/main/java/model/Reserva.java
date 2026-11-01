package model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataEntrada;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataSaida;

    private Double valorTotal = 0.0;

    @Enumerated(EnumType.STRING)
    private StatusReserva statusReserva;

    @ManyToOne
    @JoinColumn(name = "hospede_id")
    private Hospede hospede;

    @ManyToOne
    @JoinColumn(name = "quarto_id")
    private Quarto quarto;

    @ManyToMany
    @JoinTable(
        name = "tb_reserva_servico",
        joinColumns = @JoinColumn(name = "reserva_id"),
        inverseJoinColumns = @JoinColumn(name = "servico_id")
    )
    private List<Servico> servicos = new ArrayList<>();

    public Reserva() {
        this.statusReserva = StatusReserva.PENDENTE;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDataEntrada() { return dataEntrada; }
    public void setDataEntrada(LocalDate dataEntrada) { this.dataEntrada = dataEntrada; }

    public LocalDate getDataSaida() { return dataSaida; }
    public void setDataSaida(LocalDate dataSaida) { this.dataSaida = dataSaida; }

    public Double getValorTotal() { return valorTotal; }
    public void setValorTotal(Double valorTotal) { this.valorTotal = valorTotal; }

    public StatusReserva getStatusReserva() { return statusReserva; }
    public void setStatusReserva(StatusReserva statusReserva) { this.statusReserva = statusReserva; }

    public Hospede getHospede() { return hospede; }
    public void setHospede(Hospede hospede) { this.hospede = hospede; }

    public Quarto getQuarto() { return quarto; }
    public void setQuarto(Quarto quarto) { this.quarto = quarto; }

    public List<Servico> getServicos() { return servicos; }
    public void setServicos(List<Servico> servicos) { this.servicos = servicos; }
}