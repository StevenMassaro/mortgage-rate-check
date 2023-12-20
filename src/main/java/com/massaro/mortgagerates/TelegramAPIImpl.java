package com.massaro.mortgagerates;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendPhoto;

public class TelegramAPIImpl implements TelegramAPI {

    private static final String BOT_TOKEN = System.getenv("BOT_TOKEN");
    private static final String CHAT_ID = System.getenv("CHAT_ID");
    private static final TelegramBot telegramBot = new TelegramBot(BOT_TOKEN);

    @Override
    public void sendPhoto(byte[] results) {
        SendPhoto sendPhoto = new SendPhoto(CHAT_ID, results);
        telegramBot.execute(sendPhoto);
    }
}
