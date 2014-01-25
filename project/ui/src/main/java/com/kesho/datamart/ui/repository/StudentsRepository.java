package com.kesho.datamart.ui.repository;

import com.kesho.datamart.dto.EducationDto;
import com.kesho.datamart.dto.Page;
import com.kesho.datamart.dto.StudentDto;
import javafx.beans.Observable;
import javafx.util.Callback;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 11/18/13
 * Time: 10:46 PM
 * To change this template use File | Settings | File Templates.
 */
public interface StudentsRepository {
    Page<StudentDto> getPage(Integer pageNumber, Integer pageSize);

    StudentDto save(StudentDto dto);

    EducationDto addEducationHistory(EducationDto dto);

    List<EducationDto> getEducationHistory(Long studentId);

    StudentDto findOne(Long id);

    EducationDto save(EducationDto dto);

    List<StudentDto> getStudents();

    EducationDto findLatestEducation(Long studentId);
}
