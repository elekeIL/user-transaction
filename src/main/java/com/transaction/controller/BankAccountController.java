package com.transaction.controller;
import com.transaction.dto.NewBankAccountDto;
import com.transaction.pojo.BankAccountPojo;
import com.transaction.services.BankAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("bank-account")
@RequiredArgsConstructor
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @Operation(summary = "Create a new bank account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bank account created successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BankAccountPojo.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/create")
    public ResponseEntity<BankAccountPojo> createAccount(
           @RequestBody NewBankAccountDto dto) {

        return ResponseEntity.ok().body(bankAccountService.createBankAccount(dto));
    }

    @Operation(summary = "Get bank account details and balance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account details retrieved successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BankAccountPojo.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid account number or bank code", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/balance")
    public ResponseEntity<BankAccountPojo> getAccountDetails(
            @Parameter(description = "Account number", required = true) @RequestParam String accountNumber,
            @Parameter(description = "Bank code", required = true) @RequestParam String bankCode) {

        return ResponseEntity.ok().body(bankAccountService.getAvailableBalance(accountNumber, bankCode));
    }
}


