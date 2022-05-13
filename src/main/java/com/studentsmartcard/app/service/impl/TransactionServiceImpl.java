package com.studentsmartcard.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentsmartcard.app.models.PaymentDTO;
import com.studentsmartcard.app.models.Student;
import com.studentsmartcard.app.models.Transaction;
import com.studentsmartcard.app.models.enums.TransactionType;
import com.studentsmartcard.app.repository.TransactionRepository;
import com.studentsmartcard.app.service.StudentService;
import com.studentsmartcard.app.service.TransactionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

		@Autowired
		private StudentService studentService;

		@Autowired
		private TransactionRepository transactionRepository;
		
		// ADMIN only feature
		@Override
		public Double recharge(PaymentDTO dto) {
			log.debug("[transaction service]: adding to student's balance for cardId: {}", dto.getCardId());
			
			if(dto.getAmount() <= 0.0) {
				throw new RuntimeException("Recharge amount must be greater than 0.0 for card: " + dto.getCardId());
			}
			
			Student student = studentService.getByCardId(dto.getCardId());
			Double existingAmt = student.getAmount();
			Double updatedAmt = existingAmt + dto.getAmount();
			student.setAmount(updatedAmt);
			studentService.update(student);
			Transaction recharge = new Transaction.Builder(dto.getCardId(), dto.getAmount())
					.setByRole(dto.getUpdatedBy())
					.setByEmail(dto.getByEmail())
					.setFromEmail(student.getEmail())
					.setStudentId(student.getId())
					.setType(TransactionType.CREDIT)
					.build();
			log.debug("[transaction service]: saving credit transaction for cardId: {}", dto.getCardId());
			transactionRepository.save(recharge);
			return updatedAmt;
		}
		
		// CANTEEN, STATIONARY, LIBRARY only feature
		@Override
		public Double pay(PaymentDTO dto) {
			log.debug("[transaction service]: removing from student's balance for cardId: {}", dto.getCardId());
			Student student = studentService.getByCardId(dto.getCardId());
			Double existingAmt = student.getAmount();
			if(existingAmt >= dto.getAmount()) {
				Double updatedAmt = existingAmt - dto.getAmount();
				student.setAmount(updatedAmt);
				studentService.update(student);
				Transaction payment = new Transaction.Builder(dto.getCardId(), dto.getAmount())
						.setByRole(dto.getUpdatedBy())
						.setByEmail(dto.getByEmail())
						.setFromEmail(student.getEmail())
						.setStudentId(student.getId())
						.setType(TransactionType.DEBIT)
						.build();
				log.debug("[transaction service]: saving debit transaction for cardId: {}", dto.getCardId());
				transactionRepository.save(payment);
				return updatedAmt;
			}
			throw new RuntimeException("Insufficient balance in account, please recharge to pay for card: " + dto.getCardId());
			
		}

		@Override
		public List<Transaction> getRecentByCardId(String cardId) {
			log.debug("[transaction service]: getting recent transactions by card: {}", cardId);
			return transactionRepository.findTop5ByCardIdOrderByDateDesc(cardId);
		}

}
