package com.kesho.datamart.service;

import com.kesho.datamart.dto.Page;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.paging.PageRequest;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 11/20/13
 * Time: 5:54 PM
 * To change this template use File | Settings | File Templates.
 */
public interface StudentService {
    //List<StudentDto> getAll();

    StudentDto get(Long id);

    Page<StudentDto> getPage(PageRequest request);
}
