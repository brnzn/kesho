package com.kesho.datamart.ui.repository;

import com.kesho.datamart.dto.FamilyDto;
import com.kesho.datamart.dto.Page;
import com.kesho.datamart.paging.Request;
import com.kesho.datamart.service.FamilyService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/21/13
 * Time: 6:02 PM
 * To change this template use File | Settings | File Templates.
 */
@Named
public class FamilyRepositoryImpl implements FamilyRepository {
    @Inject
    private FamilyService familyService;

    public FamilyDto save(FamilyDto dto) {
        return familyService.save(dto);
    }

    @Override
    public List<FamilyDto> getFamilies() {
        return familyService.getFamilies();
    }

    @Override
    public Page<FamilyDto> getPage(int page, int pageSize) {
        return familyService.getPage(new Request(page, pageSize));
    }
}
