package ru.otus.hw15rest.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw15rest.dtos.*;
import ru.otus.hw15rest.entities.Account;
import ru.otus.hw15rest.entities.Transfer;
import ru.otus.hw15rest.exceptions_handling.ResourceNotFoundException;
import ru.otus.hw15rest.services.TransfersService;

import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/transfers")
public class TransfersController {
    private final TransfersService transfersService;

    private static final Function<Transfer, TransferDto> ENTITY_TO_DTO = t -> new TransferDto(t.getId(), t.getAccountSource().getClientId(), t.getAccountTarget().getClientId(), t.getAccountTarget().getAccountId(), t.getAccountTarget().getAccountId(), t.getMessage(), t.getAmount());
    private static final Function<Account, AccountDto> ACCOUNT_DTO = account -> new AccountDto(account.getClientId(), account.getAccountId(), account.getBalance());

    public TransfersController(TransfersService transfersService) {
        this.transfersService = transfersService;
    }

    @GetMapping
    public TransfersPageDto getAllTransfers(@RequestHeader(name = "client-id") String clientId) {
        return new TransfersPageDto(
                transfersService
                        .getAllTransfers(clientId)
                        .stream()
                        .map(ENTITY_TO_DTO).collect(Collectors.toList())
        );
    }

    @GetMapping("/accounts")
    public AccountsPageDto getAllAccounts(@RequestHeader(name = "client-id") String clientId) {
        return new AccountsPageDto(
                transfersService
                        .getAccountAllByClientId(clientId)
                        .stream()
                        .map(ACCOUNT_DTO).collect(Collectors.toList())
        );
    }



    @GetMapping("/{id}")
    public TransferDto getTransferById(@RequestHeader(name = "client-id") String clientId, @PathVariable String id) {
        return ENTITY_TO_DTO.apply(transfersService.getTransferById(id, clientId).orElseThrow(() -> new ResourceNotFoundException("Перевод не найден")));
    }

    @GetMapping("/account/{id}")
    public AccountDto getAccountById(@PathVariable String id, @RequestHeader(name = "client-id") String clientId) {
        return ACCOUNT_DTO.apply(transfersService.getAccountAllByClientId(id,clientId).orElseThrow(() -> new ResourceNotFoundException("Акант не найден")));
    }

    @PostMapping
    public void executeTransfer(@RequestHeader(name = "client-id") String clientId, @RequestBody ExecuteTransferDtoRq executeTransferDtoRq) {
        transfersService.execute(clientId, executeTransferDtoRq);
    }

    @GetMapping("/hello")
    public ResponseEntity<String> printGreeting() {
        return ResponseEntity.ok("Hello World!");
    }
}
