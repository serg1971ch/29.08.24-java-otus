package ru.otus.hw15rest.dtos;


public record TransferDto(String id,
                          String clientSource, String clientTarget,
                          String accountSource, String accountTarget,
                          String message, int amount) {
}