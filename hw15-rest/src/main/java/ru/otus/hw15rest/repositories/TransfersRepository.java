package ru.otus.hw15rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.otus.hw15rest.entities.Transfer;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransfersRepository extends JpaRepository<Transfer, String> {

    @Query("SELECT t FROM Transfer t WHERE t.id = :id AND (t.accountSourceId.clientId = :clientId OR t.accountTargetId.clientId = :clientId)")
    Optional<Transfer> findByTransferIdAndAccountClientId(@Param("id") String id, @Param("clientId") String clientId);

    @Query("SELECT t FROM Transfer t WHERE t.accountSourceId.clientId = :clientId OR t.accountTargetId.clientId = :clientId")
    List<Transfer> findAllByClientId(@Param("clientId") String clientId);
}
