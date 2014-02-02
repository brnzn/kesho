package com.kesho.datamart.ui.repository;

import com.kesho.datamart.dto.FamilyDto;
import com.kesho.datamart.dto.Page;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/21/13
 * Time: 6:11 PM
 * To change this template use File | Settings | File Templates.
 */
public interface FamilyRepository {
    FamilyDto save(FamilyDto dto);

    List<FamilyDto> getFamilies();

    Page<FamilyDto> getPage(int page, int pageSize);
}
