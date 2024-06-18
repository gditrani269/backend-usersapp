package com.ger.backend.usersapp.backendusersapp.models.dto.mapper;

public class DtoMapperUser {

    private static DtoMapperUser mapper;
    private DtoMapperUser () {

    }

    public static DtoMapperUser getInstance () {
        mapper = new DtoMapperUser ();
        return mapper;
    }
}
