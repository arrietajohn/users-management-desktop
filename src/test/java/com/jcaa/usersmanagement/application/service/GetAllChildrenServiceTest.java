package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jcaa.usersmanagement.application.port.out.GetAllChildrenPort;
import com.jcaa.usersmanagement.domain.enums.GuardianRelationship;
import com.jcaa.usersmanagement.domain.model.ChildModel;
import com.jcaa.usersmanagement.domain.valueobject.ChildFullName;
import com.jcaa.usersmanagement.domain.valueobject.ChildId;
import com.jcaa.usersmanagement.domain.valueobject.GuardianId;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/** Tests para GetAllChildrenService: lista con elementos y lista vacía. */
@DisplayName("GetAllChildrenService")
@ExtendWith(MockitoExtension.class)
class GetAllChildrenServiceTest {

  @Mock private GetAllChildrenPort getAllChildrenPort;

  private GetAllChildrenService service;

  @BeforeEach
  void setUp() {
    service = new GetAllChildrenService(getAllChildrenPort);
  }

  private static ChildModel existingChild() {
    return new ChildModel(
        new ChildId("M-001"),
        new ChildFullName("Ana", "Maria", "Gomez", "Perez"),
        LocalDate.of(2022, 3, 10),
        LocalDate.of(2024, 1, 15),
        null,
        null,
        new GuardianId("1001"),
        GuardianRelationship.FAMILIAR);
  }

  @Test
  @DisplayName("execute() devuelve los niños que entrega el puerto")
  void shouldReturnChildrenFromPort() {
    // Arrange
    when(getAllChildrenPort.getAll()).thenReturn(List.of(existingChild()));

    // Act
    final List<ChildModel> result = service.execute();

    // Assert
    assertEquals(1, result.size());
    assertEquals("M-001", result.get(0).getId().value());
    verify(getAllChildrenPort).getAll();
  }

  @Test
  @DisplayName("execute() devuelve una lista vacía cuando no hay niños")
  void shouldReturnEmptyListWhenThereAreNoChildren() {
    // Arrange
    when(getAllChildrenPort.getAll()).thenReturn(List.of());

    // Act & Assert
    assertTrue(service.execute().isEmpty());
  }
}
