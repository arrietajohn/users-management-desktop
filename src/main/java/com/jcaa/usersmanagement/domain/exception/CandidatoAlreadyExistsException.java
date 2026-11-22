package com.jcaa.usersmanagement.domain.exception;

public class CandidatoAlreadyExistsException extends DomainException{
    public CandidatoAlreadyExistsException (String id){
        super ("El candidato con el ID "+id+" ya existe.");
    }
}
