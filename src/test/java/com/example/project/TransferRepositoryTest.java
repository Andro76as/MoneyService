package com.example.project;

import com.example.project.model.Amount;
import com.example.project.model.ConfirmOperationRequest;
import com.example.project.model.TransferRequest;
import com.example.project.repository.TransferRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class TransferRepositoryTest {
    @Autowired
    private TransferRepository repository;
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
    void getOperationId() {
        int expected = 1;
        assertEquals(repository.getOperationId(), expected);
    }

    @Test
    void addAndRemoveTransfer() {
        TransferRequest beforeAdd = repository.removeTransfer(OPERATION_ID);
        assertNull(beforeAdd);
        repository.addTransfer(OPERATION_ID, TRANSFER_REQUEST);
        TransferRequest afterAdd = repository.removeTransfer(OPERATION_ID);
        assertEquals(afterAdd, TRANSFER_REQUEST);
    }

    @Test
    void addAndRemoveCode() {
        String beforeAdd = repository.removeCode(OPERATION_ID);
        assertNull(beforeAdd);
        repository.addCode(OPERATION_ID, CODE);
        String afterAdd = repository.removeCode(OPERATION_ID);
        assertEquals(afterAdd, CODE);
    }
}