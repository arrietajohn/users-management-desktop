package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ChildResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.ChildController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.ChildResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ListChildrenHandler implements OperationHandler {

  private final ChildController childController;
  private final ChildResponsePrinter printer;

  @Override
  public void handle() {
    final List<ChildResponse> children = childController.listAllChildren();
    printer.printList(children);
  }
}
