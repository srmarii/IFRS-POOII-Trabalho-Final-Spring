package repository;

import model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    @Query("select count(r) from Reserva r where r.quarto.id = :quartoId "
            + "and (r.situacao is null or r.situacao <> 'Cancelada') "
            + "and r.dataEntrada <= :saida and r.dataSaida >= :entrada "
            + "and r.id <> :ignorarId")
    long contarConflitos(@Param("quartoId") Long quartoId,
                         @Param("entrada") LocalDate entrada,
                         @Param("saida") LocalDate saida,
                         @Param("ignorarId") Long ignorarId);
}
