package com.kesho.datamart.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/9/13
 * Time: 8:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class DBUtil {
    public static <T> T findOne(JpaTransactionManager transactionManager, Class<T> entity, final JpaRepository<T, Long> dao, final Long id) {
        TransactionCallback<T> callback = new TransactionCallback<T>() {
            @Override
            public T doInTransaction(TransactionStatus status) {
                return dao.findOne(id);
            }
        };

        TransactionTemplate txTemplate = new TransactionTemplate(transactionManager);
        txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

        return txTemplate.execute(callback);
    }
}
