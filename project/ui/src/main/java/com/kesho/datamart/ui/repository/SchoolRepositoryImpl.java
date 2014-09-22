package com.kesho.datamart.ui.repository;

import com.kesho.datamart.dto.Page;
import com.kesho.datamart.dto.SchoolDto;
import com.kesho.datamart.paging.Request;
import com.kesho.datamart.service.SchoolService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/9/13
 * Time: 6:04 PM
 * To change this template use File | Settings | File Templates.
 */
@Named("InstitutionRepository")
public class SchoolRepositoryImpl implements SchoolRepository {
    @Inject
    private SchoolService service;

    @Override
    public SchoolDto create(SchoolDto institution) {
        return service.save(institution);
    }

    @Override
    public Page<SchoolDto> getPage(int page, int pageSize) {
        return service.getPage(new Request(page, pageSize));
    }

    @Override
    public List<SchoolDto> getAllSchools() {
        return service.getAllSchools();
    }
}
