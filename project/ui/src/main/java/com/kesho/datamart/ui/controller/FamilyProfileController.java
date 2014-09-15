package com.kesho.datamart.ui.controller;

import com.kesho.datamart.dto.FamilyDto;
import com.kesho.datamart.dto.HistoryDto;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.repository.FamilyRepository;
import com.kesho.datamart.ui.repository.StudentsRepository;

import javax.inject.Inject;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/15/13
 * Time: 12:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class FamilyProfileController extends AbstractHistoryController<FamilyDto> {
    @Inject
    private FamilyRepository familyRepository;

    public FamilyProfileController() {
        WindowsUtil.getInstance().autowire(this);
    }

    @Override
    protected List<HistoryDto> getData(Long ownerId) {
        return familyRepository.getFamilyProfile(ownerId);
    }

    @Override
    protected HistoryDto saveDto(HistoryDto dto) {
        return familyRepository.save(dto);
    }

    @Override
    protected void deleteDto(Long id) {
        familyRepository.deleteHistory(id);
    }
}
