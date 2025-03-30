package ru.otus.hw15rest.dtos;

import ru.otus.hw15rest.dtos.TransferDto;

import java.util.List;

public record TransfersPageDto(List<TransferDto> entries) {

}