package com.test.payment.paymenttest.repositories;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.test.payment.paymenttest.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
    // 
    /**
     * Method findAllByCustomerIdAndPaymentTypeIdAndDateAndIdWithPagination to generate query ORM when accessing payment records
     * 
     * Using non-mandatory key indicates with 'Optional' typed (non-mandatory filters),
     * query "(<column>=<value> OR <value> IS NULL)" will return TRUE if "value" has null or non-null (this query needs for non-mandatory field),
     * therefore when using custom query(for Optional field), need to use tag @Query
     * 
     * An Optional with Date-typed column need to be casted to avoid error in JPA when generate sql.
     * 
     * Pagination need to be passed with key size and offset(page),
     * see {@link com.test.payment.paymenttest.services.PaymentService#getPayments} for details access of these keys
     * 
     * @param customerId
     * @param paymentTypeId
     * @param date
     * @param id
     * @param pageable
     * @return
     */
    
    @Query(value = "SELECT id, amount, customerId, date, paymentTypeId FROM Payment WHERE customerId=?1 AND paymentTypeId=?2 AND (date=?3 OR CAST(?3 AS DATE) IS NULL) AND (id=?4 OR ?4 IS NULL)")
    Slice<Payment> findAllByCustomerIdAndPaymentTypeIdAndDateAndId (
        int customerId,
        int paymentTypeId,
        Optional<Date> date,
        Optional<Integer> id,
        Pageable pageable);
}
