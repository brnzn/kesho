package com.kesho.datamart.service;

import com.kesho.datamart.dto.*;
import com.kesho.datamart.paging.Request;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 11/20/13
 * Time: 5:54 PM
 * To change this template use File | Settings | File Templates.
 */
public interface StudentService {
    StudentDto get(Long id);

    Page<StudentDto> getPage(Request request);

    StudentDto save(StudentDto dto);

    List<EducationDto> getEducationHistory(Long studentId);

    EducationDto save(EducationDto dto);

    List<StudentDto> getStudents();

    EducationDto findLatestEducation(Long studentId);

    void deleteEducationHistory(Long id);

    EducationDto getLastYearEducationLog(Long studentId);

    List<HistoryDto> getStudentHistory(Long studentId);

    void deleteStudentHistory(Long id);

    HistoryDto save(HistoryDto dto);

    List<ContactDetailDto> getStudentContacts(Long ownerId);

    void deleteContact(Long id);

    ContactDetailDto save(ContactDetailDto dto);
}
