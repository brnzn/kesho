package com.kesho.datamart.ui.repository;

import com.kesho.datamart.dto.Page;
import com.kesho.datamart.dto.PaymentArrangementDto;
import com.kesho.datamart.dto.SponsorDto;
import com.kesho.datamart.paging.Request;
import com.kesho.datamart.service.PaymentArrangementService;
import com.kesho.datamart.service.SponsorService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 11/18/13
 * Time: 11:10 PM
 * To change this template use File | Settings | File Templates.
 */
@Named("SponsorsRepositoryImpl")
public class SponsorsRepositoryImpl implements SponsorsRepository {
    //TODO: PaymentArrangementService should be part of SponsorService
    @Inject
    private SponsorService sponsorService;
    @Inject
    private PaymentArrangementService paymentArrangementService;

    @Override
    public Page<SponsorDto> getPage(Integer pageNumber, Integer pageSize) {
        return sponsorService.getPage(new Request(pageNumber, pageSize));
    }

    @Override
    public SponsorDto save(SponsorDto dto) {
        return sponsorService.save(dto);
    }

    @Override
    public PaymentArrangementDto save(PaymentArrangementDto dto) {
        return paymentArrangementService.save(dto);
    }

    @Override
    public List<PaymentArrangementDto> getPaymentArrangements(Long sponsorId) {
        return paymentArrangementService.getPaymentArrangements(sponsorId);
    }

    @Override
    public void deleteSponsor(Long id) {
        sponsorService.deleteSponsor(id);
    }
}
