package model;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_quarto")
public class Quarto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numero;
    private Integer capacidade;
    private Double valorDiaria;

    @Enumerated(EnumType.STRING)
    private StatusQuarto statusQuarto;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    public Quarto() {
        this.statusQuarto = StatusQuarto.DISPONIVEL;
    }

    public Quarto(Long id, Integer numero, Integer capacidade, Double valorDiaria, Hotel hotel) {
        this.id = id;
        this.numero = numero;
        this.capacidade = capacidade;
        this.valorDiaria = valorDiaria;
        this.hotel = hotel;
        this.statusQuarto = StatusQuarto.DISPONIVEL;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }

    public Integer getCapacidade() { return capacidade; }
    public void setCapacidade(Integer capacidade) { this.capacidade = capacidade; }

    public Double getValorDiaria() { return valorDiaria; }
    public void setValorDiaria(Double valorDiaria) { this.valorDiaria = valorDiaria; }

    public StatusQuarto getStatusQuarto() { return statusQuarto; }
    public void setStatusQuarto(StatusQuarto statusQuarto) { this.statusQuarto = statusQuarto; }

    public Hotel getHotel() { return hotel; }
    public void setHotel(Hotel hotel) { this.hotel = hotel; }
}