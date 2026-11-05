package repository;

import model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    /**
     * Mesma regra do ReservaDAO.isQuartoDisponivel do Swing: conta reservas
     * não canceladas do quarto cujo período se sobrepõe (extremos inclusos).
     * Passe -1 em ignorarId ao criar uma reserva nova.
     */
    @Query("select count(r) from Reserva r where r.quarto.id = :quartoId "
            + "and (r.situacao is null or r.situacao <> 'Cancelada') "
            + "and r.dataEntrada <= :saida and r.dataSaida >= :entrada "
            + "and r.id <> :ignorarId")
    long contarConflitos(@Param("quartoId") Long quartoId,
                         @Param("entrada") LocalDate entrada,
                         @Param("saida") LocalDate saida,
                         @Param("ignorarId") Long ignorarId);
}
