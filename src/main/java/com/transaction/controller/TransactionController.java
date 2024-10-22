package com.transaction.controller;

import com.transaction.dto.NewTransactionDto;
import com.transaction.entity.TransactionHistory;
import com.transaction.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @Operation(summary = "Perform a transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction performed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TransactionHistory.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<TransactionHistory> performTransaction(
            @RequestBody NewTransactionDto newTransaction,
            @Parameter(description = "Account number", required = true) @RequestParam String accountNumber,
            @Parameter(description = "Bank code", required = true) @RequestParam String bankCode) {

        return ResponseEntity.ok().body(transactionService.executeTransaction(newTransaction, accountNumber, bankCode));
    }
}
