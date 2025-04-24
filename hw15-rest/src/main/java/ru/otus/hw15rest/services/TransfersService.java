package ru.otus.hw15rest.services;

import java.util.List;
import java.util.Optional;

import ru.otus.hw15rest.dtos.ExecuteTransferDtoRq;
import ru.otus.hw15rest.entities.Account;
import ru.otus.hw15rest.entities.Transfer;

public interface TransfersService {
    Optional<Transfer> getTransferById(String id, String clientId);
    List<Transfer> getAllTransfers(String clientId);
    Transfer execute(String clientId, ExecuteTransferDtoRq executeTransferDtoRq);
//    Optional<Account> getAccountByIdAndClientId(String id, String clientId);
    List<Account> getAccountAllByClientId(String clientId);

    Optional<Account> getAccountAllByClientId(String id, String clientId);
}
