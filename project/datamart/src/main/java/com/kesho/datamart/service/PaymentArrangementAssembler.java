package com.kesho.datamart.service;

import com.kesho.datamart.dto.PaymentArrangementDto;
import com.kesho.datamart.entity.PaymentArrangement;

import java.util.Currency;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/25/13
 * Time: 9:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class PaymentArrangementAssembler {
    public PaymentArrangement toEntity(PaymentArrangementDto dto) {
        PaymentArrangement entity = new PaymentArrangement();
        entity.setId(dto.getId());
        entity.setVersion(dto.getVersion());
        entity.setEndDate(dto.getEndDate());
        entity.setAmount(dto.getAmount());
        entity.setSponsorId(dto.getSponsorId());
        entity.setStudentId(dto.getStudentId());
        entity.setStartDate(dto.getStartDate());
        entity.setType(dto.getFinancialArrangement());
        entity.setCurrency(dto.getCurrency().getCurrencyCode());
        return entity;
    }

    public PaymentArrangementDto toDto(PaymentArrangement entity) {
        PaymentArrangementDto dto = new PaymentArrangementDto();
        dto.setId(entity.getId());
        dto.setVersion(entity.getVersion());
        dto.setEndDate(entity.getEndDate());
        dto.setAmount(entity.getAmount());
        dto.setSponsorId(entity.getSponsorId());
        dto.setStudentId(entity.getStudentId());
        dto.setStartDate(entity.getStartDate());
        dto.setFinancialArrangement(entity.getType());
        dto.setCurrency(Currency.getInstance(entity.getCurrency()));
        return  dto;
    }

}
