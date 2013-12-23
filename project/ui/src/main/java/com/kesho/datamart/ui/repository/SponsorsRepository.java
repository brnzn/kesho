package com.kesho.datamart.ui.repository;

import com.kesho.datamart.dto.Page;
import com.kesho.datamart.dto.SponsorDto;

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
}
