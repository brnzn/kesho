package com.kesho.datamart.ui.repository;

import com.kesho.datamart.dto.Page;
import com.kesho.datamart.dto.SponsorDto;
import com.kesho.datamart.paging.Request;
import com.kesho.datamart.service.SponsorService;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 11/18/13
 * Time: 11:10 PM
 * To change this template use File | Settings | File Templates.
 */
@Named("SponsorsRepositoryImpl")
public class SponsorsRepositoryImpl implements SponsorsRepository {
    @Inject
    private SponsorService service;

    @Override
    public Page<SponsorDto> getPage(Integer pageNumber, Integer pageSize) {
        return service.getPage(new Request(pageNumber, pageSize));
    }

    @Override
    public SponsorDto save(SponsorDto dto) {
        return service.save(dto);
    }
}
