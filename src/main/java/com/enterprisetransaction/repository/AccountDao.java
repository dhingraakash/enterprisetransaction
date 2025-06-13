package com.enterprisetransaction.repository;

import com.enterprisetransaction.entity.Account;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Account findById(Long id) {
        return entityManager.find(Account.class, id);
    }

    public void update(Account account) {
        entityManager.merge(account);
    }
}
