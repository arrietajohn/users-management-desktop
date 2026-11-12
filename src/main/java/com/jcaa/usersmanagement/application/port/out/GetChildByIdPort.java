package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.ChildModel;
import com.jcaa.usersmanagement.domain.valueobject.ChildId;
import java.util.Optional;

public interface GetChildByIdPort {
  Optional<ChildModel> getById(ChildId childId);
}
