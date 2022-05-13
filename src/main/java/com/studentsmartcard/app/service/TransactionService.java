package com.studentsmartcard.app.service;

import java.util.List;

import com.studentsmartcard.app.models.PaymentDTO;
import com.studentsmartcard.app.models.Transaction;

public interface TransactionService {
	public Double recharge(PaymentDTO paymentDTO);
	public Double pay(PaymentDTO paymentDTO);
	
	public List<Transaction> getRecentByCardId(String cardId);
}
