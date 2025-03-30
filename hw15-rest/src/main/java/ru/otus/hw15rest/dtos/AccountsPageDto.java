package ru.otus.hw15rest.dtos;

import ru.otus.hw15rest.dtos.AccountDto;

import java.util.List;

public record AccountsPageDto(List<AccountDto> accountDtoList) {
}
