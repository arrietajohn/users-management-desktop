package com.jcaa.usersmanagement.domain.model;

import com.jcaa.usersmanagement.domain.valueobject.StudentGroup;
import com.jcaa.usersmanagement.domain.valueobject.StudentId;
import com.jcaa.usersmanagement.domain.valueobject.StudentName;
import lombok.Value;

@Value
public class StudentModel {

  StudentId id;
  StudentName name;
  StudentGroup group;

  public static StudentModel create(
      final StudentId id, final StudentName name, final StudentGroup group) {
    return new StudentModel(id, name, group);
  }
}
