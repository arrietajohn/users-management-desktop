package com.jcaa.usersmanagement.domain.exception;

public class CandidatoNotFoundException extends DomainException {
    public CandidatoNotFoundException(String id) {
        super ("No se encontro un candidato con el ID: "+id);
    }
}
