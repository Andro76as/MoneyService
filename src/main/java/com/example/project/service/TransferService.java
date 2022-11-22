package com.example.project.service;

import com.example.project.exceptions.InputDataException;
import com.example.project.model.ConfirmOperationRequest;
import com.example.project.model.TransferRequest;
import com.example.project.model.TransferResponse;
import com.example.project.repository.TransferRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Slf4j
@Service
@AllArgsConstructor
public class TransferService {
    private final TransferRepository repository;

    public TransferResponse response (TransferRequest request) throws InputDataException {
        final String cardFrom = request.getCardFromNumber();
        final String cardFromValidTill = request.getCardFromValidTill();
        final String cardFromCVV = request.getCardFromCVV();
        final String cardToNumber = request.getCardToNumber();
        final int value = request.getAmount().getValue();
        final String transferId = Integer.toString(repository.getOperationId());
        int commission = (int) (0.01 * value);

        cardCheck(cardFrom, cardToNumber);
        cvvCheck(cardFromCVV);
        dateCheck(cardFromValidTill);
        amountCheck(value);

        repository.addTransfer(transferId, request);
        String code = (int) (100 + Math.random() * 900) + "";
        repository.addCode(transferId, code);

        log.info("[{}] Новый перевод: СardFrom {}, CardTo {}, amount {}, commission {}",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")),
                cardFrom, cardToNumber, value, commission);
        return new TransferResponse(transferId);
    }

    public TransferResponse confirmOperation(ConfirmOperationRequest confirmOperationRequest) {
        final String operationId = confirmOperationRequest.getOperationId();

        final TransferRequest transferRequest = repository.removeTransfer(operationId);
        if (transferRequest == null) {
            ResponseEntity.badRequest().body("Данные отсутствуют");
        }
        final String code = repository.removeCode(operationId);
        log.info("[{}] Перевод одобрен: OperationId {}, code {}",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")), operationId, code);
        return new TransferResponse(operationId);
    }

    private void cardCheck (String cardFrom, String cardTo) throws InputDataException {
        if (cardFrom == null) {
            throw new InputDataException("Номер карты отправителя обязателен");
        } else if (cardTo == null) {
            throw new InputDataException("Номер карты получателя обязателен");
        } else if (!cardFrom.matches("[0-9]{16}")){
            throw new InputDataException("Номер карты отправителя должен быть 16 символов");
        } else if (!cardTo.matches("[0-9]{16}")){
            throw new InputDataException("Номер карты получателя должен быть 16 символов");
        }
    }

    private void cvvCheck (String cvv) throws InputDataException{
        if (cvv == null) {
            throw new InputDataException("CVC / CVC2 номер карты отправителя обязателен");
        } else if (cvv.length()>0 && !cvv.matches("[0-9]{3}")) {
            throw new InputDataException("CVC / CVC2 код отправителя должен быть 3 символов");
        }
    }

    private void dateCheck (String period) throws InputDataException{
        StringBuilder builder = new StringBuilder(period);
        int cardMonth = Integer.parseInt(builder.substring(0, 2 ));
        if (cardMonth > 12){
            throw new InputDataException("Текущий месяц не может быть больше 12");
        }
        int cardYear = Integer.parseInt("20" + builder.substring(3, 5 ));
        if (LocalDate.now().getYear() <= cardYear) {
            if (LocalDate.now().getMonthValue() > cardMonth) {
                throw new InputDataException("Истекла дата действия карты отправителя");
            }
        } else {
            throw new InputDataException("Истекла дата действия карты отправителя");
        }
    }

    private void amountCheck (Integer amount) throws InputDataException{
        if (amount <= 0) {
            throw new InputDataException("Необходимо указать сумму перевода");
        }
    }
}
