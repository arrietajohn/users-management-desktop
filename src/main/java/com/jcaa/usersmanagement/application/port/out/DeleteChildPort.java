package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.valueobject.ChildId;

public interface DeleteChildPort {
  void delete(ChildId childId);
}
