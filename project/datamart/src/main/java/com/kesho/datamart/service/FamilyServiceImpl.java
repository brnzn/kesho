package com.kesho.datamart.service;

import com.kesho.datamart.dto.FamilyDto;
import com.kesho.datamart.entity.Family;
import com.kesho.datamart.repository.FamilyDAO;
import org.springframework.beans.factory.parsing.FailFastProblemReporter;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/21/13
 * Time: 5:50 PM
 * To change this template use File | Settings | File Templates.
 */
@Named
public class FamilyServiceImpl implements FamilyService {
    @Inject
    private FamilyDAO dao;
    private FamilyAssembler assembler = new FamilyAssembler();

    public FamilyDto save(FamilyDto family) {
        return assembler.toDto(dao.save(assembler.toFamily(family)));
    }

    @Override
    public List<FamilyDto> getFamilies() {
        return assembler.toDto(dao.findAll());
    }
}
