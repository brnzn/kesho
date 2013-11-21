package com.kesho.datamart.service;

import com.kesho.datamart.dto.Page;
import com.kesho.datamart.dto.PageImpl;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.paging.PageRequest;
import com.kesho.datamart.repository.StudentsDAO;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

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
    private StudentsDAO studentsRepository;
    private StudentsAssembler assembler = new StudentsAssembler();

    private Validator validator;

    public StudentServiceImpl() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
//    @Override
//    public List<StudentDto> getAll() {
//        return assembler.toDto(studentsRepository.findAll());
//    }
//
    @Override
    public StudentDto get(Long id) {
        return assembler.toDto(studentsRepository.findOne(id));
    }

    @Override
    public Page<StudentDto> getPage(PageRequest request) {
        Set<ConstraintViolation<PageRequest>> violations = validator.validate(request);

        return new PageImpl<StudentDto>().withErrorMessage(violations.iterator().next().getMessage());
    }

//    private Set<String> validate(StudentDto dto) {
//        Set<ConstraintViolation<StudentDto>> violations = validator.validate(dto);
//
//        if (!violations.isEmpty()) {
//            for (ConstraintViolation<StudentDto> violation:violations) {
//
//            }
//        }
//    }
}
