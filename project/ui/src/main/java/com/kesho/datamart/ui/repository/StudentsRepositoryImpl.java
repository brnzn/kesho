package com.kesho.datamart.ui.repository;

import com.kesho.datamart.dto.Page;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.paging.Request;
import com.kesho.datamart.service.StudentService;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 11/18/13
 * Time: 11:10 PM
 * To change this template use File | Settings | File Templates.
 */
@Named("StudentsRepositoryImpl")
public class StudentsRepositoryImpl implements StudentsRepository {
    @Inject
    private StudentService studentsService;

    @Override
    public Page<StudentDto> getPage(Integer pageNumber, Integer pageSize) {
        return studentsService.getPage(new Request(pageNumber, pageSize));
    }

    @Override
    public StudentDto save(StudentDto dto) {
        return studentsService.save(dto);
    }

}
