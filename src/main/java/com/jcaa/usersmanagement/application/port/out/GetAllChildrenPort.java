package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.ChildModel;
import java.util.List;

public interface GetAllChildrenPort {
  List<ChildModel> getAll();
}
