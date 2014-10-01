package com.kesho.datamart.service;

import com.kesho.datamart.dto.*;
import com.kesho.datamart.entity.ContactDetail;
import com.kesho.datamart.entity.EducationHistory;
import com.kesho.datamart.entity.Student;
import com.kesho.datamart.entity.StudentHistory;
import com.kesho.datamart.paging.Request;
import com.kesho.datamart.repository.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
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
    private StudentHistoryDAO historyDAO;

    @Inject
    private EducationHistoryDAO educationHistoryDAO;

    @Inject
    private ContactDetailsDAO contactDAO;

    private StudentsAssembler assembler = new StudentsAssembler();
    private EducationAssembler educationAssembler = new EducationAssembler();
    private StudentHistoryAssembler historyAssembler = new StudentHistoryAssembler();
    private ContactDetailAssembler contactAssembler = new ContactDetailAssembler();

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
    @Override
    public StudentDto save(StudentDto dto) {
        return assembler.toDto(doSave(dto));
    }

    //This method is needed for version property, which get updated only after the transaction commit
    //TODO: use aspectj so the method don't need to be public
    @Transactional(readOnly = false)
    public Student doSave(StudentDto dto) {
        Student student = assembler.toStudent(dto);
        student.setFamily(familyDAO.findOne(dto.getFamily().getId()));
        return studentsDao.save(student);
    }

    @Override
    public List<EducationDto> getEducationHistory(Long studentId) {
        return educationAssembler.toDto(educationHistoryDAO.findByStudentId(studentId));
    }

    @Override
    public EducationDto save(EducationDto dto) {
        return educationAssembler.toDto(doSave(dto));
    }

    //This method is needed for version property, which get updated only after the transaction commit
    //TODO: use aspectj so the method don't need to be public
    @Transactional(readOnly = false)
    public EducationHistory doSave(EducationDto dto) {
        EducationHistory log = educationAssembler.toLog(dto);
        if(dto.getInstitution() != null) {
            log.setSchool(schoolsDao.findOne(dto.getInstitution().getId()));
        }

        return educationHistoryDAO.save(log);
    }

    @Override
    public List<StudentDto> getStudents() {
        return assembler.toDto(studentsDao.findAll());
    }

    public EducationDto findLatestEducation(Long studentId) {
        return educationHistoryDAO.findLatestEducation(studentId);
    }

    @Override
    @Transactional
    public void deleteEducationHistory(Long id) {
        educationHistoryDAO.deleteById(id);
    }

    @Override
    public EducationDto getLastYearEducationLog(Long studentId) {
        List<EducationHistory> educations = educationHistoryDAO.getEducationSortedByDate(studentId);
        if(educations.isEmpty()) {
            return null;
        }

        return educationAssembler.toDto(educations.get(0));
    }

    @Override
    public List<HistoryDto> getStudentHistory(Long studentId) {
        return historyAssembler.toDto(historyDAO.findByStudentId(studentId));
    }

    //TODO: pass dto for version check
    @Override
    @Transactional
    public void deleteStudentHistory(Long id) {
        historyDAO.delete(id);
    }

    @Override
    public HistoryDto save(HistoryDto dto) {
        StudentHistory history = historyDAO.save(historyAssembler.toStudentHistory(dto));
        return historyAssembler.toDto(history);
    }

    //This method is needed for version property, which get updated only after the transaction commit
    //TODO: use aspectj so the method don't need to be public
    @Transactional(readOnly = false)
    public ContactDetail doSave(ContactDetailDto dto) {
        return contactDAO.save(contactAssembler.toEntity(dto));
    }
    private Page<StudentDto> toPageResult(final org.springframework.data.domain.Page<Student> page, final Request request) {
        if(page.getTotalElements() > 0 && page.getTotalPages() <= request.getPageNumber()) {
            return new PageImpl<StudentDto>().withError(String.format("Max pages is [%s]", page.getTotalPages())).withTotalPages(page.getTotalPages());
        }

        Page<StudentDto> result = new PageImpl<StudentDto>().withContent(assembler.toDto(page.getContent())).withTotalPages(page.getTotalPages()).withPageSize(page.getNumberOfElements());
        return result;
    }
}
