package com.kesho.datamart.service;

import com.kesho.datamart.dto.Page;
import com.kesho.datamart.dto.PageImpl;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.entity.Student;
import com.kesho.datamart.paging.Request;
import com.kesho.datamart.repository.StudentsDAO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
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

    @Override
    public StudentDto get(Long id) {
        return assembler.toDto(studentsRepository.findOne(id));
    }

    @Override
    public Page<StudentDto> getPage(Request request) {
        List<String> errors = validate(request);

        if(errors != null) {
            return new PageImpl<StudentDto>().withErrors(errors);
        }

        Pageable pageSpecification = new PageRequest(request.getPageNumber(), request.getPageSize());
//                , new Sort(
//                Sort.Direction.ASC, "firstName"));

        return toPageResult(studentsRepository.findAll(pageSpecification), request);
    }

    //TODO: validate
    @Transactional(readOnly = false)
    @Override
    public StudentDto save(StudentDto dto) {
        Student student = assembler.toStudent(dto);
        return assembler.toDto(studentsRepository.save(student));
    }

    private Page<StudentDto> toPageResult(final org.springframework.data.domain.Page<Student> page, final Request request) {
        if(page.getTotalElements() > 0 && page.getTotalPages() <= request.getPageNumber()) {
            return new PageImpl<StudentDto>().withError(String.format("Max pages is [%s]", page.getTotalPages())).withTotalPages(page.getTotalPages());
        }

        StudentsAssembler assembler = new StudentsAssembler();
        Page<StudentDto> result = new PageImpl<StudentDto>().withContent(assembler.toDto(page.getContent())).withTotalPages(page.getTotalPages()).withPageSize(page.getNumberOfElements());
        return result;
    }

    private List<String> validate(Request request) {
        Set<ConstraintViolation<Request>> violations = validator.validate(request);

        List<String> errors = null;
        if (!violations.isEmpty()) {
            errors = new ArrayList<String>();
            for (ConstraintViolation<Request> violation:violations) {
                errors.add(violation.getMessage());
            }
        }

        return errors;
    }
}
