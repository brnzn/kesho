package com.kesho.datamart.ui.repository;

import com.kesho.datamart.dto.Page;
import com.kesho.datamart.dto.PaymentArrangementDto;
import com.kesho.datamart.dto.SponsorDto;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 11/18/13
 * Time: 10:46 PM
 * To change this template use File | Settings | File Templates.
 */
public interface SponsorsRepository {
    Page<SponsorDto> getPage(Integer pageNumber, Integer pageSize);

    SponsorDto save(SponsorDto dto);

    PaymentArrangementDto save(PaymentArrangementDto dto);

    List<PaymentArrangementDto> getPaymentArrangements(Long sponsorId);

    void deleteSponsor(Long id);
}
