package com.kesho.datamart.ui.controller;

import com.kesho.datamart.dto.HistoryDto;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.ui.WindowsUtil;
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
public class StudentHistoryController extends AbstractHistoryController<StudentDto> {
    @Inject
    private StudentsRepository studentsRepository;

    public StudentHistoryController() {
        WindowsUtil.getInstance().autowire(this);
    }

    @Override
    protected List<HistoryDto> getData(Long ownerId) {
        return studentsRepository.getStudentHistory(ownerId);
    }

    @Override
    protected HistoryDto saveDto(HistoryDto dto) {
        return studentsRepository.save(dto);
    }

    @Override
    protected void deleteDto(Long id) {
        studentsRepository.deleteStudentHistory(id);
    }
}
