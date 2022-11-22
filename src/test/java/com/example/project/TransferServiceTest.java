package com.example.project;

import com.example.project.exceptions.InputDataException;
import com.example.project.model.Amount;
import com.example.project.model.ConfirmOperationRequest;
import com.example.project.model.TransferRequest;
import com.example.project.model.TransferResponse;
import com.example.project.service.TransferService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TransferServiceTest {
    @Autowired
    private TransferService service;
    private static long suiteStartTime;
    private long testStartTime;
    public static final String OPERATION_ID = "1";
    private static final String CODE = "1234";
    public static final TransferRequest TRANSFER_REQUEST = new TransferRequest("5555555555555556",
            "12/24", "123", "5555555555555555", new Amount(5000_00, "EUR"));
    public static final ConfirmOperationRequest CONFIRM_OPERATION_REQUEST = new ConfirmOperationRequest(
            OPERATION_ID, CODE);

    @BeforeAll
    public static void initSuite() {
        System.out.println("Running StringTest");
        suiteStartTime = System.nanoTime();
    }

    @AfterAll
    public static void completeSuite() {
        System.out.println("StringTest complete: " + (System.nanoTime() - suiteStartTime));
    }

    @BeforeEach
    public void initTest() {
        System.out.println("Starting new nest");
        testStartTime = System.nanoTime();
    }

    @AfterEach
    public void finalizeTest() {
        System.out.println("Test complete:" + (System.nanoTime() - testStartTime));
    }

    @Test
    void transfer() throws InputDataException {
        TransferResponse expected = new TransferResponse(OPERATION_ID);
        TransferResponse actual = service.response(TRANSFER_REQUEST);
        assertEquals(expected, actual);
    }

    @Test
    void confirmOperation() {
        TransferResponse expected = new TransferResponse(OPERATION_ID);
        TransferResponse actual = service.confirmOperation(CONFIRM_OPERATION_REQUEST);
        assertEquals(expected, actual);
    }
}
