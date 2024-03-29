package com.example.CAPSTONE.Repositories;

import com.example.CAPSTONE.Models.Event;
import com.example.CAPSTONE.Models.TipoEvento;
import com.example.CAPSTONE.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepo extends JpaRepository<Event, Long>, PagingAndSortingRepository<Event, Long> {
    List<Event> findByNomeContainingIgnoreCase(String nome);

    List<Event> findByTipoEvento(TipoEvento tipoEvento);

    @Query(
            "SELECT e FROM Event AS e WHERE(:tipoEvento is null or e.tipoEvento = :tipoEvento) AND (:luogo is null or e.luogo = :luogo) AND  (:nome is null or e.nome ILIKE concat('%',:nome,'%'))")
    List<Event> findByFiltri(@Param("tipoEvento") TipoEvento tipoEvento ,@Param("luogo") String luogo, @Param("nome") String nome);

}
