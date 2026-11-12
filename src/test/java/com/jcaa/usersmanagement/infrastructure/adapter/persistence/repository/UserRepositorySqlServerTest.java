package com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.domain.enums.UserRole;
import com.jcaa.usersmanagement.domain.enums.UserStatus;
import com.jcaa.usersmanagement.domain.exception.UserNotFoundException;
import com.jcaa.usersmanagement.domain.model.UserModel;
import com.jcaa.usersmanagement.domain.valueobject.UserEmail;
import com.jcaa.usersmanagement.domain.valueobject.UserId;
import com.jcaa.usersmanagement.domain.valueobject.UserName;
import com.jcaa.usersmanagement.domain.valueobject.UserPassword;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.exception.PersistenceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Tests for UserRepositorySqlServer.
 *
 * <p>Covers all public methods with their branches: save() — happy path, INSERT failure, user not
 * found after insert (orElseThrow); update() — happy path, UPDATE failure; getById() — found, not
 * found, SQLException; getByEmail() — found, not found, SQLException; getAll() — happy path,
 * SQLException; delete() — happy path, SQLException.
 */
@DisplayName("UserRepositorySqlServer")
@ExtendWith(MockitoExtension.class)
class UserRepositorySqlServerTest {

  private static final String ID = "u-001";
  private static final String NAME = "John Doe";
  private static final String EMAIL = "john@example.com";
  private static final String HASH = "$2a$12$abcdefghijklmnopqrstuO";
  private static final String ROLE = "ADMIN";
  private static final String STATUS = "ACTIVE";
  private static final String CREATED_AT = "2024-01-01";
  private static final String UPDATED_AT = "2024-01-02";

  @Mock private Connection connection;
  @Mock private PreparedStatement statement;
  @Mock private ResultSet resultSet;

  private UserRepositorySqlServer repository;
  private UserModel userModel;
  private UserId userId;
  private UserEmail userEmail;

  @BeforeEach
  void setUp() {
    repository = new UserRepositorySqlServer(connection);
    userId = new UserId(ID);
    userEmail = new UserEmail(EMAIL);
    userModel =
        new UserModel(
            userId,
            new UserName(NAME),
            userEmail,
            UserPassword.fromHash(HASH),
            UserRole.ADMIN,
            UserStatus.ACTIVE);
  }

  // Helper: wire connection → statement → resultSet
  private void configureStatementAndResultSet() throws SQLException {
    when(connection.prepareStatement(anyString())).thenReturn(statement);
    when(statement.executeQuery()).thenReturn(resultSet);
  }

  // Helper: configure resultSet to return one full user row
  private void configureResultSetRow() throws SQLException {
    when(resultSet.getString("id")).thenReturn(ID);
    when(resultSet.getString("name")).thenReturn(NAME);
    when(resultSet.getString("email")).thenReturn(EMAIL);
    when(resultSet.getString("password")).thenReturn(HASH);
    when(resultSet.getString("role")).thenReturn(ROLE);
    when(resultSet.getString("status")).thenReturn(STATUS);
    when(resultSet.getString("created_at")).thenReturn(CREATED_AT);
    when(resultSet.getString("updated_at")).thenReturn(UPDATED_AT);
  }

  // ── save() — happy path

  @Test
  @DisplayName("save() executes INSERT and returns the persisted user fetched by id")
  void shouldSaveUserAndReturnById() throws SQLException {
    // Arrange
    configureStatementAndResultSet();
    when(resultSet.next()).thenReturn(true);
    configureResultSetRow();

    // Act
    final UserModel result = repository.save(userModel);

    // Assert
    assertAll(
        "save() happy path",
        () -> assertEquals(ID, result.getId().value(), "id"),
        () -> assertEquals(NAME, result.getName().value(), "name"),
        () -> assertEquals(EMAIL, result.getEmail().value(), "email"));
  }

  // ── save() — INSERT fails → PersistenceException

  @Test
  @DisplayName("save() throws PersistenceException when INSERT raises SQLException")
  void shouldThrowPersistenceExceptionWhenInsertFails() throws SQLException {
    // Arrange
    when(connection.prepareStatement(anyString())).thenReturn(statement);
    when(statement.executeUpdate()).thenThrow(new SQLException("Insert failed"));

    // Act + Assert
    assertThrows(
        PersistenceException.class,
        () -> repository.save(userModel),
        "must throw PersistenceException when INSERT raises SQLException");
  }

  // ── save() → findByIdOrFail — user not found after insert → UserNotFoundException

  @Test
  @DisplayName("save() throws UserNotFoundException when the saved user cannot be found")
  void shouldThrowUserNotFoundExceptionWhenUserNotFoundAfterSave() throws SQLException {
    // Arrange
    configureStatementAndResultSet();
    when(resultSet.next()).thenReturn(false);

    // Act + Assert
    assertThrows(
        UserNotFoundException.class,
        () -> repository.save(userModel),
        "must throw UserNotFoundException when SELECT returns no rows after INSERT");
  }

  // ── update() — happy path

  @Test
  @DisplayName("update() executes UPDATE and returns the refreshed user fetched by id")
  void shouldUpdateUserAndReturnById() throws SQLException {
    // Arrange
    configureStatementAndResultSet();
    when(resultSet.next()).thenReturn(true);
    configureResultSetRow();

    // Act
    final UserModel result = repository.update(userModel);

    // Assert
    assertEquals(ID, result.getId().value(), "id must match the updated user");
  }

  // ── update() — UPDATE fails → PersistenceException

  @Test
  @DisplayName("update() throws PersistenceException when UPDATE raises SQLException")
  void shouldThrowPersistenceExceptionWhenUpdateFails() throws SQLException {
    // Arrange
    when(connection.prepareStatement(anyString())).thenReturn(statement);
    when(statement.executeUpdate()).thenThrow(new SQLException("Update failed"));

    // Act + Assert
    assertThrows(
        PersistenceException.class,
        () -> repository.update(userModel),
        "must throw PersistenceException when UPDATE raises SQLException");
  }

  // ── getById() — row found → Optional.of(user)

  @Test
  @DisplayName("getById() returns Optional.of(user) when a matching row exists")
  void shouldReturnUserWhenFound() throws SQLException {
    // Arrange
    configureStatementAndResultSet();
    when(resultSet.next()).thenReturn(true);
    configureResultSetRow();

    // Act
    final Optional<UserModel> result = repository.getById(userId);

    // Assert
    assertAll(
        "getById() found",
        () -> assertTrue(result.isPresent(), "must be present"),
        () -> assertEquals(ID, result.get().getId().value(), "id"));
  }

  // ── getById() — no row → Optional.empty()

  @Test
  @DisplayName("getById() returns Optional.empty() when no matching row exists")
  void shouldReturnEmptyWhenNotFound() throws SQLException {
    // Arrange
    configureStatementAndResultSet();
    when(resultSet.next()).thenReturn(false);

    // Act
    final Optional<UserModel> result = repository.getById(userId);

    // Assert
    assertTrue(result.isEmpty(), "must return Optional.empty() when no row matches the id");
  }

  // ── getById() — SQLException → PersistenceException (from prepareStatement)

  @Test
  @DisplayName("getById() throws PersistenceException when prepareStatement raises SQLException")
  void shouldThrowPersistenceExceptionOnGetByIdFailure() throws SQLException {
    // Arrange
    when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Query failed"));

    // Act + Assert
    assertThrows(
        PersistenceException.class,
        () -> repository.getById(userId),
        "must throw PersistenceException when prepareStatement raises SQLException");
  }

  // ── getById() — SQLException → PersistenceException (from executeQuery, inside try body)

  @Test
  @DisplayName("getById() throws PersistenceException when executeQuery raises SQLException")
  void shouldThrowPersistenceExceptionWhenGetByIdExecuteQueryFails() throws SQLException {
    // Arrange
    when(connection.prepareStatement(anyString())).thenReturn(statement);
    when(statement.executeQuery()).thenThrow(new SQLException("Execute query failed"));

    // Act + Assert
    assertThrows(
        PersistenceException.class,
        () -> repository.getById(userId),
        "must throw PersistenceException when executeQuery raises SQLException inside the try block");
  }

  // ── getById() — SQLException → PersistenceException (from statement.close() after normal exit)

  @Test
  @DisplayName(
      "getById() throws PersistenceException when PreparedStatement.close() raises SQLException")
  void shouldThrowPersistenceExceptionWhenGetByIdStatementCloseFails() throws SQLException {
    // Arrange
    when(connection.prepareStatement(anyString())).thenReturn(statement);
    when(statement.executeQuery()).thenReturn(resultSet);
    when(resultSet.next()).thenReturn(false);
    doThrow(new SQLException("Close failed")).when(statement).close();

    // Act + Assert
    assertThrows(
        PersistenceException.class,
        () -> repository.getById(userId),
        "must throw PersistenceException when PreparedStatement.close() raises SQLException after normal body exit");
  }

  // ── getByEmail() — row found → Optional.of(user)

  @Test
  @DisplayName("getByEmail() returns Optional.of(user) when a matching row exists")
  void shouldReturnUserByEmailWhenFound() throws SQLException {
    // Arrange
    configureStatementAndResultSet();
    when(resultSet.next()).thenReturn(true);
    configureResultSetRow();

    // Act
    final Optional<UserModel> result = repository.getByEmail(userEmail);

    // Assert
    assertAll(
        "getByEmail() found",
        () -> assertTrue(result.isPresent(), "must be present"),
        () -> assertEquals(EMAIL, result.get().getEmail().value(), "email"));
  }

  // ── getByEmail() — no row → Optional.empty()

  @Test
  @DisplayName("getByEmail() returns Optional.empty() when no matching row exists")
  void shouldReturnEmptyWhenEmailNotFound() throws SQLException {
    // Arrange
    configureStatementAndResultSet();
    when(resultSet.next()).thenReturn(false);

    // Act
    final Optional<UserModel> result = repository.getByEmail(userEmail);

    // Assert
    assertTrue(result.isEmpty(), "must return Optional.empty() when no row matches the email");
  }

  // ── getByEmail() — SQLException → PersistenceException (from prepareStatement)

  @Test
  @DisplayName("getByEmail() throws PersistenceException when prepareStatement raises SQLException")
  void shouldThrowPersistenceExceptionOnGetByEmailFailure() throws SQLException {
    // Arrange
    when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Query failed"));

    // Act + Assert
    assertThrows(
        PersistenceException.class,
        () -> repository.getByEmail(userEmail),
        "must throw PersistenceException when prepareStatement raises SQLException");
  }

  // ── getByEmail() — SQLException → PersistenceException (from executeQuery, inside try body)

  @Test
  @DisplayName("getByEmail() throws PersistenceException when executeQuery raises SQLException")
  void shouldThrowPersistenceExceptionWhenGetByEmailExecuteQueryFails() throws SQLException {
    // Arrange
    when(connection.prepareStatement(anyString())).thenReturn(statement);
    when(statement.executeQuery()).thenThrow(new SQLException("Execute query failed"));

    // Act + Assert
    assertThrows(
        PersistenceException.class,
        () -> repository.getByEmail(userEmail),
        "must throw PersistenceException when executeQuery raises SQLException inside the try block");
  }

  // ── getByEmail() — SQLException → PersistenceException (from statement.close() after normal
  // exit)

  @Test
  @DisplayName(
      "getByEmail() throws PersistenceException when PreparedStatement.close() raises SQLException")
  void shouldThrowPersistenceExceptionWhenGetByEmailStatementCloseFails() throws SQLException {
    // Arrange
    when(connection.prepareStatement(anyString())).thenReturn(statement);
    when(statement.executeQuery()).thenReturn(resultSet);
    when(resultSet.next()).thenReturn(false);
    doThrow(new SQLException("Close failed")).when(statement).close();

    // Act + Assert
    assertThrows(
        PersistenceException.class,
        () -> repository.getByEmail(userEmail),
        "must throw PersistenceException when PreparedStatement.close() raises SQLException after normal body exit");
  }

  // ── getAll() — happy path

  @Test
  @DisplayName("getAll() returns one model per row in the result set")
  void shouldReturnAllUsers() throws SQLException {
    // Arrange
    configureStatementAndResultSet();
    when(resultSet.next()).thenReturn(true, false);
    configureResultSetRow();

    // Act
    final List<UserModel> result = repository.getAll();

    // Assert
    assertAll(
        "getAll() happy path",
        () -> assertEquals(1, result.size(), "list size"),
        () -> assertEquals(ID, result.get(0).getId().value(), "first user id"));
  }

  // ── getAll() — SQLException → PersistenceException

  @Test
  @DisplayName("getAll() throws PersistenceException when the query raises SQLException")
  void shouldThrowPersistenceExceptionOnGetAllFailure() throws SQLException {
    // Arrange
    when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Query failed"));

    // Act + Assert
    assertThrows(
        PersistenceException.class,
        () -> repository.getAll(),
        "must throw PersistenceException when SELECT raises SQLException");
  }

  // ── delete() — happy path

  @Test
  @DisplayName("delete() executes DELETE without throwing")
  void shouldDeleteUserWithoutThrowing() throws SQLException {
    // Arrange
    when(connection.prepareStatement(anyString())).thenReturn(statement);

    // Act + Assert
    assertDoesNotThrow(
        () -> repository.delete(userId),
        "delete() must not throw when DELETE executes successfully");
  }

  // ── delete() — SQLException → PersistenceException

  @Test
  @DisplayName("delete() throws PersistenceException when DELETE raises SQLException")
  void shouldThrowPersistenceExceptionWhenDeleteFails() throws SQLException {
    // Arrange
    when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Delete failed"));

    // Act + Assert
    assertThrows(
        PersistenceException.class,
        () -> repository.delete(userId),
        "must throw PersistenceException when DELETE raises SQLException");
  }
}
