package repository;

import model.Quarto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuartoRepository extends JpaRepository<Quarto, Long> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "UPDATE tb_quarto SET numero = :numero, capacidade = :capacidade, "
            + "valor_diaria = :valor, hotel_id = :hotelId, tipo = :tipo WHERE id = :id",
            nativeQuery = true)
    int atualizar(@Param("id") Long id,
                  @Param("numero") Integer numero,
                  @Param("capacidade") Integer capacidade,
                  @Param("valor") Double valor,
                  @Param("hotelId") Long hotelId,
                  @Param("tipo") String tipo);
}
