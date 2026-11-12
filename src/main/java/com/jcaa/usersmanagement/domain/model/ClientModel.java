package com.jcaa.usersmanagement.domain.model;

import com.jcaa.usersmanagement.domain.enums.ClientActivityType;
import com.jcaa.usersmanagement.domain.valueobject.ClientCode;
import com.jcaa.usersmanagement.domain.valueobject.ClientName;
import com.jcaa.usersmanagement.domain.valueobject.ClientPhone;
import lombok.Value;

/**
 * Cliente de la Agencia de Castings (empresa de moda o de publicidad y cine).
 *
 * <p>Corresponde a la tabla CLIENTE del modelo entidad-relacion.
 */
@Value
public class ClientModel {

  ClientCode code;
  ClientName name;
  String address;
  ClientPhone phone;
  String contactPerson;
  ClientActivityType activityType;

  public static ClientModel create(
      final ClientCode code,
      final ClientName name,
      final String address,
      final ClientPhone phone,
      final String contactPerson,
      final ClientActivityType activityType) {
    return new ClientModel(code, name, address, phone, contactPerson, activityType);
  }

  public boolean isFashionCompany() {
    return activityType == ClientActivityType.FASHION;
  }
}
