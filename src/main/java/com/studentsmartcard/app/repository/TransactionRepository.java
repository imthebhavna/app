package com.studentsmartcard.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.studentsmartcard.app.models.Transaction;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, String> {

	public List<Transaction> findByCardIdOrderByDateDesc(String cardId);
	
	@Query(value="SELECT t FROM Transaction t WHERE t.cardId = :cardId ORDER BY t.date DESC")
	public List<Transaction> findRecentByCardId(@Param("cardId") String cardId);
	
	public List<Transaction> findTop5ByCardIdOrderByDateDesc(String cardId);
	
}
