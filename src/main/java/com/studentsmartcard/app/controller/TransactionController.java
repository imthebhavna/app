package com.studentsmartcard.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.studentsmartcard.app.models.PaymentDTO;
import com.studentsmartcard.app.models.Transaction;
import com.studentsmartcard.app.service.TransactionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	@PostMapping(value="/pay")
	public Double payment(@RequestBody PaymentDTO paymentDTO) {
		log.debug("[transaction controller]: payment of {} for card: {} deduced by {}", paymentDTO.getAmount(), paymentDTO.getCardId(), paymentDTO.getUpdatedBy().getValue());
		return transactionService.pay(paymentDTO);
	}
	
	@PostMapping(value="/recharge")
	public Double recharge(@RequestBody PaymentDTO paymentDTO) {
		log.debug("[transaction controller]: recharging amount: {} for card: {} added by {}", paymentDTO.getAmount(), paymentDTO.getCardId(), paymentDTO.getUpdatedBy().getValue());
		return transactionService.recharge(paymentDTO);
	}
	
	@GetMapping(value="/transactions/{cardId}")
	public List<Transaction> getTransactions(@PathVariable String cardId) {
		return transactionService.getRecentByCardId(cardId);
	}
}
