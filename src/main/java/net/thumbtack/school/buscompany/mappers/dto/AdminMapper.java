package net.thumbtack.school.buscompany.mappers.dto;

import net.thumbtack.school.buscompany.dto.request.account.RegistrationAdminDtoRequest;
import net.thumbtack.school.buscompany.dto.response.account.InfoAdministratorDtoResponse;
import net.thumbtack.school.buscompany.dto.response.account.RegistrationAdminDtoResponse;
import net.thumbtack.school.buscompany.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    AdminMapper INSTANCE = Mappers.getMapper(AdminMapper.class);

    Account registrationAdminDtoToAccount(RegistrationAdminDtoRequest response);

    RegistrationAdminDtoResponse accountToDto(Account account);

    InfoAdministratorDtoResponse accountToDtoInfo(Account account);
}
