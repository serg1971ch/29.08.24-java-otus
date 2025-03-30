package ru.otus.hw15rest.dtos;


public record ExecuteTransferDtoRq( String id,
        String clientSource, String clientTarget,String sourceAccount,
        String targetAccount, String message, int amount) {
}