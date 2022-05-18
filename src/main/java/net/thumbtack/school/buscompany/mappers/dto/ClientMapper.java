package net.thumbtack.school.buscompany.mappers.dto;

import net.thumbtack.school.buscompany.dto.request.account.RegistrationClientDtoRequest;
import net.thumbtack.school.buscompany.dto.response.account.InfoClientDtoResponse;
import net.thumbtack.school.buscompany.dto.response.account.RegistrationClientDtoResponse;
import net.thumbtack.school.buscompany.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);


    Account registrationDtoToAccount(RegistrationClientDtoRequest response);

    RegistrationClientDtoResponse accountToDto(Account account);

    InfoClientDtoResponse accountToDtoInfo(Account account);
}
