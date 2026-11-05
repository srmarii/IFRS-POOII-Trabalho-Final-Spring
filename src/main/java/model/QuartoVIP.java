package model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("VIP")
public class QuartoVIP extends Quarto {

    public static final double FATOR_VIP = 1.5;

    public QuartoVIP() {}

    @Override
    public double calcularValorDiaria() {
        return base() * FATOR_VIP;
    }

    @Override
    public String getTipo() {
        return "VIP";
    }
}
