package ru.otus.hw15rest.services;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.hw15rest.dtos.ExecuteTransferDtoRq;
import ru.otus.hw15rest.entities.Account;
import ru.otus.hw15rest.entities.Transfer;
import ru.otus.hw15rest.exceptions_handling.ValidationException;
import ru.otus.hw15rest.exceptions_handling.ValidationFieldError;
import ru.otus.hw15rest.repositories.AccountsRepository;
import ru.otus.hw15rest.repositories.TransfersRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log
@Service
public class TransfersServiceImpl implements TransfersService {
    private final TransfersRepository transfersRepository;
    private final AccountsRepository accountsRepository;

    @Autowired
    public TransfersServiceImpl(TransfersRepository transfersRepository, AccountsRepository accountsRepository) {
        this.transfersRepository = transfersRepository;
        this.accountsRepository = accountsRepository;
    }

    public List<Account> getAccountAllByClientId(String clientId) {
        return accountsRepository.findAllByClientId(clientId);
    }

    @Override
    public Optional<Account> getAccountAllByClientId(String id, String clientId) {
        return accountsRepository.findByIdAndClientId(id, clientId);
    }

    public Optional<Transfer> getTransferById(String id, String clientId) {
        return transfersRepository.findByTransferIdAndAccountClientId(id, clientId);
    }

    public List<Transfer> getAllTransfers(String clientId) {
        return transfersRepository.findAllByClientId(clientId);
    }

    public Transfer execute(String clientId, ExecuteTransferDtoRq executeTransferDtoRq) {
        validateExecuteTransferDtoRq(executeTransferDtoRq);
        Account accountSource = accountsRepository.findByClientId(executeTransferDtoRq.clientSource());
        Account accountTarget = accountsRepository.findByClientId(executeTransferDtoRq.clientTarget());
        log.info("Account source: " + accountSource.getClientId());
        log.info("Account target: " + accountTarget.getClientId());
        Transfer transfer = new Transfer(executeTransferDtoRq.id(), accountSource, accountTarget, executeTransferDtoRq.message(), executeTransferDtoRq.amount());
        validateIsBlocked(accountSource, transfer);
        if(!accountSource.isBlocked()) {
            accountSource.setBalance(accountSource.getBalance() - executeTransferDtoRq.amount());
            accountTarget.setBalance(accountTarget.getBalance() + executeTransferDtoRq.amount());
        }
        transfersRepository.save(transfer);
        log.info("Transfer created " + transfer.getAmount());
        return transfer;
    }

    private void validateExecuteTransferDtoRq(ExecuteTransferDtoRq executeTransferDtoRq) {
        List<ValidationFieldError> errors = new ArrayList<>();
        if (executeTransferDtoRq.sourceAccount().length() != 12) {
            errors.add(new ValidationFieldError("sourceAccount", "Длина поля счет отправителя должна составлять 12 символов"));
        }
        if (executeTransferDtoRq.targetAccount().length() != 12) {
            errors.add(new ValidationFieldError("targetAccount", "Длина поля счет получателя должна составлять 12 символов"));
        }
        if (executeTransferDtoRq.amount() <= 0) {
            errors.add(new ValidationFieldError("amount", "Сумма перевода должна быть больше 0"));
        }
        if (!errors.isEmpty()) {
            throw new ValidationException("EXECUTE_TRANSFER_VALIDATION_ERROR", "Проблемы заполнения полей перевода", errors);
        }
    }

    private boolean validateIsBlocked(Account account, Transfer transfer) {
        boolean blocked = account.isBlocked();
        if(account.getBalance()<transfer.getAmount()||
                transfer.getAmount()==0 || transfer.getAccountSource()!=null) {
            blocked = true;
        }
        return blocked;
    }
}
