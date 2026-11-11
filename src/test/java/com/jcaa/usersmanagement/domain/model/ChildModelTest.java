package com.jcaa.usersmanagement.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.enums.GuardianRelationship;
import com.jcaa.usersmanagement.domain.exception.InvalidChildDatesException;
import com.jcaa.usersmanagement.domain.valueobject.ChildAddress;
import com.jcaa.usersmanagement.domain.valueobject.ChildFullName;
import com.jcaa.usersmanagement.domain.valueobject.ChildId;
import com.jcaa.usersmanagement.domain.valueobject.GuardianId;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChildModelTest {

  private static final LocalDate BIRTH_DATE = LocalDate.of(2020, 1, 10);
  private static final LocalDate ADMISSION_DATE = LocalDate.of(2024, 2, 20);
  private static final LocalDate DISCHARGE_DATE = LocalDate.of(2025, 3, 15);

  @Test
  @DisplayName("Should create an active child and preserve all provided fields")
  void shouldCreateActiveChildAndPreserveFields() {
    final ChildId id = new ChildId("CH-001");
    final ChildFullName fullName = new ChildFullName("First", "Second", "Surname", "Other");
    final ChildAddress address = new ChildAddress("1 Example Street");
    final GuardianId guardianId = new GuardianId("G-001");

    final ChildModel child =
        ChildModel.create(
            id,
            fullName,
            BIRTH_DATE,
            ADMISSION_DATE,
            address,
            guardianId,
            GuardianRelationship.FAMILIAR);

    assertAll(
        () -> assertSame(id, child.getId()),
        () -> assertSame(fullName, child.getFullName()),
        () -> assertEquals(BIRTH_DATE, child.getBirthDate()),
        () -> assertEquals(ADMISSION_DATE, child.getAdmissionDate()),
        () -> assertNull(child.getDischargeDate()),
        () -> assertTrue(child.isActive()),
        () -> assertSame(address, child.getAddress()),
        () -> assertSame(guardianId, child.getGuardianId()),
        () -> assertEquals(GuardianRelationship.FAMILIAR, child.getGuardianRelationship()));
  }

  @Test
  @DisplayName("Should create a child without an address")
  void shouldCreateChildWithoutAddress() {
    final ChildModel child =
        ChildModel.create(
            new ChildId("CH-001"),
            validFullName(),
            BIRTH_DATE,
            ADMISSION_DATE,
            null,
            new GuardianId("G-001"),
            GuardianRelationship.CONOCIDO);

    assertNull(child.getAddress());
  }

  @Test
  @DisplayName("Should reject a null child id")
  void shouldRejectNullId() {
    assertThrows(
        NullPointerException.class,
        () ->
            ChildModel.create(
                null,
                validFullName(),
                BIRTH_DATE,
                ADMISSION_DATE,
                null,
                new GuardianId("G-001"),
                GuardianRelationship.FAMILIAR));
  }

  @Test
  @DisplayName("Should reject a null full name")
  void shouldRejectNullFullName() {
    assertThrows(
        NullPointerException.class,
        () ->
            ChildModel.create(
                new ChildId("CH-001"),
                null,
                BIRTH_DATE,
                ADMISSION_DATE,
                null,
                new GuardianId("G-001"),
                GuardianRelationship.FAMILIAR));
  }

  @Test
  @DisplayName("Should reject a null guardian id")
  void shouldRejectNullGuardianId() {
    assertThrows(
        NullPointerException.class,
        () ->
            ChildModel.create(
                new ChildId("CH-001"),
                validFullName(),
                BIRTH_DATE,
                ADMISSION_DATE,
                null,
                null,
                GuardianRelationship.FAMILIAR));
  }

  @Test
  @DisplayName("Should reject a null guardian relationship")
  void shouldRejectNullGuardianRelationship() {
    assertThrows(
        NullPointerException.class,
        () ->
            ChildModel.create(
                new ChildId("CH-001"),
                validFullName(),
                BIRTH_DATE,
                ADMISSION_DATE,
                null,
                new GuardianId("G-001"),
                null));
  }

  @Test
  @DisplayName("Should reject a null birth date")
  void shouldRejectNullBirthDate() {
    final InvalidChildDatesException exception =
        assertThrows(
            InvalidChildDatesException.class,
            () ->
                ChildModel.create(
                    new ChildId("CH-001"),
                    validFullName(),
                    null,
                    ADMISSION_DATE,
                    null,
                    new GuardianId("G-001"),
                    GuardianRelationship.FAMILIAR));

    assertTrue(exception.getMessage().contains("birth date"));
  }

  @Test
  @DisplayName("Should reject a null admission date")
  void shouldRejectNullAdmissionDate() {
    final InvalidChildDatesException exception =
        assertThrows(
            InvalidChildDatesException.class,
            () ->
                ChildModel.create(
                    new ChildId("CH-001"),
                    validFullName(),
                    BIRTH_DATE,
                    null,
                    null,
                    new GuardianId("G-001"),
                    GuardianRelationship.FAMILIAR));

    assertTrue(exception.getMessage().contains("admission date"));
  }

  @Test
  @DisplayName("Should accept a birth date before the admission date")
  void shouldAcceptBirthDateBeforeAdmissionDate() {
    final ChildModel child = validChild();

    assertTrue(child.getBirthDate().isBefore(child.getAdmissionDate()));
  }

  @Test
  @DisplayName("Should reject a birth date after the admission date")
  void shouldRejectBirthDateAfterAdmissionDate() {
    assertThrows(
        InvalidChildDatesException.class,
        () -> createChild(ADMISSION_DATE.plusDays(1), ADMISSION_DATE));
  }

  @Test
  @DisplayName("Should reject a birth date equal to the admission date")
  void shouldRejectBirthDateEqualToAdmissionDate() {
    assertThrows(
        InvalidChildDatesException.class, () -> createChild(ADMISSION_DATE, ADMISSION_DATE));
  }

  @Test
  @DisplayName("Should withdraw a child immutably with a date after admission")
  void shouldWithdrawChildImmutablyWithDateAfterAdmission() {
    final ChildModel original = validChild();

    final ChildModel withdrawn = original.withdraw(DISCHARGE_DATE);

    assertAll(
        () -> assertNotSame(original, withdrawn),
        () -> assertNull(original.getDischargeDate()),
        () -> assertTrue(original.isActive()),
        () -> assertEquals(DISCHARGE_DATE, withdrawn.getDischargeDate()),
        () -> assertFalse(withdrawn.isActive()));
  }

  @Test
  @DisplayName("Should allow withdrawal on the admission date")
  void shouldAllowWithdrawalOnAdmissionDate() {
    final ChildModel child = validChild().withdraw(ADMISSION_DATE);

    assertEquals(ADMISSION_DATE, child.getDischargeDate());
    assertFalse(child.isActive());
  }

  @Test
  @DisplayName("Should reject withdrawal before the admission date")
  void shouldRejectWithdrawalBeforeAdmissionDate() {
    final InvalidChildDatesException exception =
        assertThrows(
            InvalidChildDatesException.class,
            () -> validChild().withdraw(ADMISSION_DATE.minusDays(1)));

    assertTrue(exception.getMessage().contains("discharge date"));
  }

  @Test
  @DisplayName("Should reject a discharge date before admission in the public constructor")
  void shouldRejectDischargeDateBeforeAdmissionInPublicConstructor() {
    assertThrows(
        InvalidChildDatesException.class,
        () -> newChild(ADMISSION_DATE.minusDays(1)));
  }

  @Test
  @DisplayName("Should allow a null discharge date in the public constructor")
  void shouldAllowNullDischargeDateInPublicConstructor() {
    final ChildModel child = newChild(null);

    assertNull(child.getDischargeDate());
    assertTrue(child.isActive());
  }

  @Test
  @DisplayName("Should compare children with equal values")
  void shouldCompareChildrenWithEqualValues() {
    assertEquals(validChild(), validChild());
  }

  private static ChildModel validChild() {
    return ChildModel.create(
        new ChildId("CH-001"),
        validFullName(),
        BIRTH_DATE,
        ADMISSION_DATE,
        new ChildAddress("1 Example Street"),
        new GuardianId("G-001"),
        GuardianRelationship.FAMILIAR);
  }

  private static ChildFullName validFullName() {
    return new ChildFullName("First", null, "Surname", null);
  }

  private static ChildModel createChild(
      final LocalDate birthDate, final LocalDate admissionDate) {
    return ChildModel.create(
        new ChildId("CH-001"),
        validFullName(),
        birthDate,
        admissionDate,
        null,
        new GuardianId("G-001"),
        GuardianRelationship.FAMILIAR);
  }

  private static ChildModel newChild(final LocalDate dischargeDate) {
    return new ChildModel(
        new ChildId("CH-001"),
        validFullName(),
        BIRTH_DATE,
        ADMISSION_DATE,
        dischargeDate,
        null,
        new GuardianId("G-001"),
        GuardianRelationship.FAMILIAR);
  }
}
