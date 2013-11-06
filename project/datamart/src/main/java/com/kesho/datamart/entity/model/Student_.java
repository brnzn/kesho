package com.kesho.datamart.entity.model;

import com.kesho.datamart.entity.Student;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

//need to use compiler APT processor to generate the MetaModel classes - see http://my.oschina.net/hantsy/blog/135485
@StaticMetamodel(Student.class)
public class Student_ {
	public static volatile SingularAttribute<Student, String> firstName;
}
