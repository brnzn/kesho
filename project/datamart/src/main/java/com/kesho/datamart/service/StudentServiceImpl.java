package com.kesho.datamart.service;

import com.kesho.datamart.dto.EducationDto;
import com.kesho.datamart.dto.Page;
import com.kesho.datamart.dto.PageImpl;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.entity.EducationHistory;
import com.kesho.datamart.entity.Student;
import com.kesho.datamart.paging.Request;
import com.kesho.datamart.repository.EducationHistoryDAO;
import com.kesho.datamart.repository.FamilyDAO;
import com.kesho.datamart.repository.SchoolsDAO;
import com.kesho.datamart.repository.StudentsDAO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 11/20/13
 * Time: 6:06 PM
 * To change this template use File | Settings | File Templates.
 */
@Named("StudentService")
public class StudentServiceImpl implements StudentService {
    @Inject
    private StudentsDAO studentsDao;
    @Inject
    private SchoolsDAO schoolsDao;
    @Inject
    private FamilyDAO familyDAO;

    @Inject
    private EducationHistoryDAO educationHistoryDAO;

    private StudentsAssembler assembler = new StudentsAssembler();
    private EducationAssembler educationAssembler = new EducationAssembler();

    @Override
    public StudentDto get(Long id) {
        return assembler.toDto(studentsDao.findOne(id));
    }

    @Override
    public Page<StudentDto> getPage(Request request) {
        List<String> errors = ValidationUtil.validate(request);

        if(errors != null) {
            return new PageImpl<StudentDto>().withErrors(errors);
        }

        Pageable pageSpecification = new PageRequest(request.getPageNumber(), request.getPageSize());

//                , new Sort(
//                Sort.Direction.ASC, "firstName"));

        return toPageResult(studentsDao.findWithFamily(pageSpecification), request);
    }

    //TODO: validate
    @Transactional(readOnly = false)
    @Override
    public StudentDto save(StudentDto dto) {
        Student student = assembler.toStudent(dto);
        student.setFamily(familyDAO.findOne(dto.getFamily().getId()));
        return assembler.toDto(studentsDao.save(student));
    }

    @Override
    @Transactional
    public EducationDto addEducationHistory(EducationDto dto) {
        EducationHistory log = educationAssembler.toLog(dto);
        log.setSchool(schoolsDao.findOne(dto.getInstitution().getId()));
        log = educationHistoryDAO.save(log);
        return educationAssembler.toDto(log);
    }

    @Override
    public List<EducationDto> getEducationHistory(Long studentId) {
        return educationAssembler.toDto(educationHistoryDAO.findByStudentId(studentId));
    }

    @Override
    @Transactional
    public EducationDto save(EducationDto dto) {
        //TODO: should it be find one??
        return addEducationHistory(dto);
    }

    @Override
    public List<StudentDto> getStudents() {
        return assembler.toDto(studentsDao.findAll());
    }

    public EducationDto findLatestEducation(Long studentId) {
        return educationHistoryDAO.findLatestEducation(studentId);
    }

    @Transactional
    @Override
    public void deleteStudent(Long id) {
        educationHistoryDAO.deleteByStudentId(id);
        studentsDao.deleteByStudentId(id);
    }

    private Page<StudentDto> toPageResult(final org.springframework.data.domain.Page<Student> page, final Request request) {
        if(page.getTotalElements() > 0 && page.getTotalPages() <= request.getPageNumber()) {
            return new PageImpl<StudentDto>().withError(String.format("Max pages is [%s]", page.getTotalPages())).withTotalPages(page.getTotalPages());
        }

        Page<StudentDto> result = new PageImpl<StudentDto>().withContent(assembler.toDto(page.getContent())).withTotalPages(page.getTotalPages()).withPageSize(page.getNumberOfElements());
        return result;
    }
}
