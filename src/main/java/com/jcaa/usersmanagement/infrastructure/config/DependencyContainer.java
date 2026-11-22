package com.jcaa.usersmanagement.infrastructure.config;

import com.jcaa.usersmanagement.application.port.in.CreateUserUseCase;
import com.jcaa.usersmanagement.application.port.in.DeleteUserUseCase;
import com.jcaa.usersmanagement.application.port.in.GetAllUsersUseCase;
import com.jcaa.usersmanagement.application.port.in.GetUserByIdUseCase;
import com.jcaa.usersmanagement.application.port.in.LoginUseCase;
import com.jcaa.usersmanagement.application.port.in.UpdateUserUseCase;
import com.jcaa.usersmanagement.application.service.CreateUserService;
import com.jcaa.usersmanagement.application.service.DeleteUserService;
import com.jcaa.usersmanagement.application.service.EmailNotificationService;
import com.jcaa.usersmanagement.application.service.GetAllUsersService;
import com.jcaa.usersmanagement.application.service.GetUserByIdService;
import com.jcaa.usersmanagement.application.service.LoginService;
import com.jcaa.usersmanagement.application.service.UpdateUserService;
import com.jcaa.usersmanagement.infrastructure.adapter.email.JavaMailEmailSenderAdapter;
import com.jcaa.usersmanagement.infrastructure.adapter.email.SmtpConfig;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.config.DatabaseConfig;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.config.DatabaseConnectionFactory;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository.UserRepositoryMySQL;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.UserController;


import com.jcaa.usersmanagement.application.port.in.CreateCandidatoUseCase;
import com.jcaa.usersmanagement.application.port.in.DeleteCandidatoUseCase;
import com.jcaa.usersmanagement.application.port.in.GetAllCandidatosUseCase;
import com.jcaa.usersmanagement.application.port.in.GetCandidatoByIdUseCase;
import com.jcaa.usersmanagement.application.port.in.UpdateCandidatoUseCase;
import com.jcaa.usersmanagement.application.service.CreateCandidatoService;
import com.jcaa.usersmanagement.application.service.DeleteCandidatoService;
import com.jcaa.usersmanagement.application.service.GetAllCandidatosService;
import com.jcaa.usersmanagement.application.service.GetCandidatoByIdService;
import com.jcaa.usersmanagement.application.service.UpdateCandidatoService;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository.CandidatoRepositoryMySQL;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.CandidatoController;

import java.sql.Connection;
import jakarta.validation.Validator;

public final class DependencyContainer {

  private static final String DB_HOST = "db.host";
  private static final String DB_PORT = "db.port";
  private static final String DB_NAME = "db.name";
  private static final String DB_USER = "db.username";
  private static final String DB_PASSWORD = "db.password";

  private static final String SMTP_HOST = "smtp.host";
  private static final String SMTP_PORT = "smtp.port";
  private static final String SMTP_USER = "smtp.username";
  private static final String SMTP_PASSWORD = "smtp.password";
  private static final String SMTP_FROM = "smtp.from.address";
  private static final String SMTP_FROM_NAME = "smtp.from.name";

  private final UserController userController;

  private final CandidatoController candidatoController;

  public DependencyContainer() {
    final AppProperties properties = new AppProperties();

    final Connection connection = buildDatabaseConnection(properties);
    final UserRepositoryMySQL userRepository = new UserRepositoryMySQL(connection);

    final JavaMailEmailSenderAdapter emailSender =
        new JavaMailEmailSenderAdapter(buildSmtpConfig(properties));
    final EmailNotificationService emailNotification = new EmailNotificationService(emailSender);

    // Construir Validator para las validaciones en la capa de aplicación
    final Validator validator = ValidatorProvider.buildValidator();

    final CreateUserUseCase createUserUseCase =
        new CreateUserService(userRepository, userRepository, emailNotification, validator);
    final UpdateUserUseCase updateUserUseCase =
        new UpdateUserService(userRepository, userRepository, userRepository, emailNotification, validator);
    final DeleteUserUseCase deleteUserUseCase =
        new DeleteUserService(userRepository, userRepository, validator);
    final GetUserByIdUseCase getUserByIdUseCase = new GetUserByIdService(userRepository, validator);
    final GetAllUsersUseCase getAllUsersUseCase = new GetAllUsersService(userRepository);
    final LoginUseCase loginUseCase = new LoginService(userRepository, validator);

    this.userController =
        new UserController(
            createUserUseCase,
            updateUserUseCase,
            deleteUserUseCase,
            getUserByIdUseCase,
            getAllUsersUseCase,
            loginUseCase);

    final CandidatoRepositoryMySQL candidatoRepository = new CandidatoRepositoryMySQL(connection);

    final CreateCandidatoUseCase createCandidatoUseCase = new CreateCandidatoService(candidatoRepository);
    final GetAllCandidatosUseCase getAllCandidatosUseCase2 = new GetAllCandidatosService(candidatoRepository);
    final GetCandidatoByIdUseCase getCandidatoByIdUseCase = new GetCandidatoByIdService(candidatoRepository);
    final UpdateCandidatoUseCase updateCandidatoUseCase = new UpdateCandidatoService(candidatoRepository, candidatoRepository);
    final DeleteCandidatoUseCase deleteCandidatoUseCase = new DeleteCandidatoService(candidatoRepository, candidatoRepository);

    this.candidatoController = new CandidatoController(
            createCandidatoUseCase,
            getAllCandidatosUseCase2,
            getCandidatoByIdUseCase,
            updateCandidatoUseCase,
            deleteCandidatoUseCase);
  }

  public UserController userController() {
    return userController;
  }


  public CandidatoController candidatoController() {
    return candidatoController;
  }

  private static Connection buildDatabaseConnection(final AppProperties properties) {
    final DatabaseConfig config =
        new DatabaseConfig(
            properties.get(DB_HOST),
            properties.getInt(DB_PORT),
            properties.get(DB_NAME),
            properties.get(DB_USER),
            properties.get(DB_PASSWORD));
    return DatabaseConnectionFactory.createConnection(config);
  }

  private static SmtpConfig buildSmtpConfig(final AppProperties properties) {
    return new SmtpConfig(
        properties.get(SMTP_HOST),
        properties.getInt(SMTP_PORT),
        properties.get(SMTP_USER),
        properties.get(SMTP_PASSWORD),
        properties.get(SMTP_FROM),
        properties.get(SMTP_FROM_NAME));
  }
}
