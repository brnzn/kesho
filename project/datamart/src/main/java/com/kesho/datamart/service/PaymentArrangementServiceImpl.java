package com.kesho.datamart.service;

import com.kesho.datamart.dto.PaymentArrangementDto;
import com.kesho.datamart.dto.SponsorDto;
import com.kesho.datamart.entity.PaymentArrangement;
import com.kesho.datamart.entity.Sponsor;
import com.kesho.datamart.repository.PaymentArrangementDao;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/25/13
 * Time: 8:59 PM
 * To change this template use File | Settings | File Templates.
 */
@Named
public class PaymentArrangementServiceImpl implements PaymentArrangementService {
    @Inject
    private PaymentArrangementDao paymentArrangementDao;
    private PaymentArrangementAssembler assembler = new PaymentArrangementAssembler();

    @Override
    public PaymentArrangementDto save(PaymentArrangementDto dto) {
        return assembler.toDto(doSave(dto));
    }

    //This method is needed for version property, which get updated only after the transaction commit
    //TODO: use aspectj so the method don't need to be public
    @Transactional(readOnly = false)
    public PaymentArrangement doSave(PaymentArrangementDto dto) {
        return paymentArrangementDao.save(assembler.toEntity(dto));
    }

    @Override
    public List<PaymentArrangementDto> getPaymentArrangements(Long sponsorId) {
        return paymentArrangementDao.findBySponsorId(sponsorId);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        paymentArrangementDao.deleteById(id);
    }
}
