package model;

import jakarta.persistence.*;

/**
 * Mesma hierarquia do Swing: Quarto (abstrata) -> QuartoSimples / QuartoVIP.
 * Usa a coluna "tipo" de tb_quarto como discriminador (SINGLE_TABLE),
 * exatamente a coluna que o Swing já grava ("Simples" ou "VIP").
 */
@Entity
@Table(name = "tb_quarto")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
public abstract class Quarto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numero;
    private Integer capacidade;

    // No banco a coluna se chama valor_diaria e guarda o valor BASE (igual ao Swing)
    @Column(name = "valor_diaria")
    private Double valorDiariaBase;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    public Quarto() {}

    public abstract double calcularValorDiaria();

    public abstract String getTipo();

    protected double base() {
        return valorDiariaBase == null ? 0.0 : valorDiariaBase;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }

    public Integer getCapacidade() { return capacidade; }
    public void setCapacidade(Integer capacidade) { this.capacidade = capacidade; }

    public Double getValorDiariaBase() { return valorDiariaBase; }
    public void setValorDiariaBase(Double valorDiariaBase) { this.valorDiariaBase = valorDiariaBase; }

    public Hotel getHotel() { return hotel; }
    public void setHotel(Hotel hotel) { this.hotel = hotel; }

    @Override
    public String toString() {
        return "Quarto " + numero + " (" + getTipo() + ")";
    }
}
