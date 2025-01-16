package ru.otus.javaprodi.application.services.impl;

import org.springframework.stereotype.Component;
import ru.otus.javaprodi.application.model.Player;
import ru.otus.javaprodi.application.services.IOService;
import ru.otus.javaprodi.application.services.PlayerService;

@Component
public class PlayerServiceImpl implements PlayerService {

    private IOService ioService;

    public PlayerServiceImpl(IOService ioService) {
        this.ioService = ioService;
    }

    @Override
    public Player getPlayer() {
        ioService.out("Представьтесь пожалуйста");
        String playerName = ioService.readLn("Введите имя: ");
        return new Player(playerName);
    }
}
