package br.com.feltex.bot.telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EchoBot extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return DadosBot.BOT_USER_NAME;
    }

    @Override
    public String getBotToken() {
        return DadosBot.BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            var mensagem = responder(update);
            try {
                execute(mensagem);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private SendMessage responder(Update update) {
        var textoMensagem = update.getMessage().getText().toLowerCase();
        var chatId = update.getMessage().getChatId().toString();

        var resposta = "";

        if ("data".equals(textoMensagem)) {
            resposta = getData();
        } else if (textoMensagem.startsWith("hora")) {
            resposta = getHora();
        } else if (textoMensagem.startsWith("ola") || textoMensagem.startsWith("olá") || textoMensagem.startsWith("oi")) {
            resposta = "\uD83D\uDC41️\u200D\uD83D\uDDE8 Olá, em algum momento responderei sua mensagem, ou nunca!";
        } else if (textoMensagem.startsWith("quem é você") || textoMensagem.startsWith("quem e voce")) {
            resposta = "\uD83E\uDD16 Eu sou um bot apaixonado e você?";
        } else if (textoMensagem.startsWith("/help")) {
            resposta = "Utilize um dos comandos:\nolá\ndata\nhora\npoesia\nquem é você?";
        } else if (textoMensagem.startsWith("matuto")) {
            resposta = "\uD83D\uDE21 Esse aí não vale nada...";
        } else if (textoMensagem.startsWith("poesia")) {
            resposta =" ❤️\u200D\uD83D\uDD25 Que não seja imortal, posto que é chama; mas que seja infinito enquanto dure";
        } else {
            resposta = "Calma, ainda estou aprendendo! Quer que eu te recite uma poesia?\nDigite /help para ver os comandos disponíveis.";
        }

        return SendMessage.builder()
                .text(resposta)
                .chatId(chatId)
                .build();
    }


    private String getData() {
        var formatter = new SimpleDateFormat("dd/MM/yyyy");
        return "A data atual é: " + formatter.format(new Date());
    }

    private String getHora() {
        var formatter = new SimpleDateFormat("HH:mm:ss");
        return "A hora atual é: " + formatter.format(new Date());
    }

}
