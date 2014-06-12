package com.kesho.datamart.ui.controller;

import com.kesho.datamart.dto.FamilyDto;
import com.kesho.datamart.ui.repository.FamilyRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;

/**
 */
@Named
public class FamilySelectorController extends SelectorController<FamilyDto> {
    @Inject
    private FamilyRepository repository;

    @Override
    public Collection<FamilyDto> getData() {
        return repository.getFamilies();
    }
}
