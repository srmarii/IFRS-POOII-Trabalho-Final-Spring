package model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Simples")
public class QuartoSimples extends Quarto {

    public QuartoSimples() {}

    @Override
    public double calcularValorDiaria() {
        return base();
    }

    @Override
    public String getTipo() {
        return "Simples";
    }
}
