package ru.otus.hw15rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.otus.hw15rest.entities.Account;

import java.util.List;
import java.util.Optional;

//клиент может запросить информацию по счетам(поиск по Id)
@Repository
public interface AccountsRepository extends JpaRepository<Account, String> {
    Account findByClientId(@Param("clientId") String clientId);
    Optional<Account> findByIdAndClientId(@Param("id") String id, @Param("clientId") String clientId);
    List<Account> findAllByClientId(@Param("clientId") String clientId);
}
