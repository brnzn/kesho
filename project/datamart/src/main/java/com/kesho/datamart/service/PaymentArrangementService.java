package com.kesho.datamart.service;

import com.kesho.datamart.dto.PaymentArrangementDto;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/25/13
 * Time: 8:58 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PaymentArrangementService {
    PaymentArrangementDto save(PaymentArrangementDto dto);

    List<PaymentArrangementDto> getPaymentArrangements(Long sponsorId);
}
