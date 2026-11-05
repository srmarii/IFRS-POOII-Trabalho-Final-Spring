package model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Entity
@Table(name = "tb_reserva")
public class Reserva {

    public static final String PENDENTE = "Pendente";
    public static final String CONFIRMADA = "Confirmada";
    public static final String CANCELADA = "Cancelada";
    public static final String FINALIZADA = "Finalizada";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataEntrada;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataSaida;

    private Double valorTotal;

    @Column(length = 20)
    private String situacao;

    @ManyToOne
    @JoinColumn(name = "hospede_id")
    private Hospede hospede;

    @ManyToOne
    @JoinColumn(name = "quarto_id")
    private Quarto quarto;

    public Reserva() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDataEntrada() { return dataEntrada; }
    public void setDataEntrada(LocalDate dataEntrada) { this.dataEntrada = dataEntrada; }

    public LocalDate getDataSaida() { return dataSaida; }
    public void setDataSaida(LocalDate dataSaida) { this.dataSaida = dataSaida; }

    public Double getValorTotal() { return valorTotal; }
    public void setValorTotal(Double valorTotal) { this.valorTotal = valorTotal; }

    public String getSituacao() { return situacao; }
    public void setSituacao(String situacao) { this.situacao = situacao; }

    public Hospede getHospede() { return hospede; }
    public void setHospede(Hospede hospede) { this.hospede = hospede; }

    public Quarto getQuarto() { return quarto; }
    public void setQuarto(Quarto quarto) { this.quarto = quarto; }
}
