package com.enterprisetransaction.repository;

import com.enterprisetransaction.dto.TransactionSummaryDto;
import com.enterprisetransaction.enums.TransactionType;
import com.enterprisetransaction.entity.Transaction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class TransactionDao {
    @PersistenceContext
    private EntityManager entityManager;

    public void save(Transaction tx) {
        entityManager.persist(tx);
    }

    public List<Transaction> findByAccountId(Long accId) {
        return entityManager.createQuery("FROM Transaction t WHERE t.account.id = :id", Transaction.class)
                .setParameter("id", accId)
                .getResultList();
    }

    public List<TransactionSummaryDto> getSummaryReport() {
        return entityManager.createQuery(
                "SELECT new com.enterprisetransaction.dto.TransactionSummaryDto(t.type, COUNT(t), SUM(t.amount)) " +
                        "FROM Transaction t GROUP BY t.type", TransactionSummaryDto.class
        ).getResultList();
    }

    public List<Transaction> findByTypeAndMinAmount(TransactionType type, Double minAmount) {
        return entityManager.createQuery("FROM Transaction t WHERE t.type = :type AND t.amount >= :minAmount ORDER BY t.timestamp DESC", Transaction.class)
                .setParameter("type", type)
                .setParameter("minAmount", minAmount)
                .getResultList();
    }
}
