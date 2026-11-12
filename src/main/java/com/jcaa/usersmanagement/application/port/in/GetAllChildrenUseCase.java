package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.domain.model.ChildModel;
import java.util.List;

public interface GetAllChildrenUseCase {

  List<ChildModel> execute();
}
