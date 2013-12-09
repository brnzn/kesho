package com.kesho.datamart.ui.repository;

import com.kesho.datamart.dto.InstitutionDto;
import com.kesho.datamart.service.InstitutionService;

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
public class InstitutionRepositoryImpl implements InstitutionRepository {
    @Inject
    private InstitutionService service;

    @Override
    public List<InstitutionDto> getInstitutions() {
        return service.getInstitutions();
    }

    @Override
    public InstitutionDto create(InstitutionDto institution) {
        return service.create(institution);
    }
}
