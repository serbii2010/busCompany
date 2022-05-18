package net.thumbtack.school.buscompany.mappers.dto;

import net.thumbtack.school.buscompany.dto.request.account.EditAdministratorDtoRequest;
import net.thumbtack.school.buscompany.dto.request.account.EditClientDtoRequest;
import net.thumbtack.school.buscompany.dto.request.account.RegistrationAdminDtoRequest;
import net.thumbtack.school.buscompany.dto.response.account.EditAdministratorDtoResponse;
import net.thumbtack.school.buscompany.dto.response.account.EditClientDtoResponse;
import net.thumbtack.school.buscompany.dto.response.account.InfoAdministratorDtoResponse;
import net.thumbtack.school.buscompany.dto.response.account.RegistrationAdminDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.Account;
import net.thumbtack.school.buscompany.service.AccountService;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    AdminMapper INSTANCE = Mappers.getMapper(AdminMapper.class);

    Account registrationAdminDtoToAccount(RegistrationAdminDtoRequest response);

    RegistrationAdminDtoResponse accountToDto(Account account);

    EditAdministratorDtoResponse accountEditToDto(Account account);

    void update(@MappingTarget Account account, EditAdministratorDtoRequest request, @Context AccountService service) throws ServerException;

    @AfterMapping
    default void setPassword(@MappingTarget Account account, EditAdministratorDtoRequest request, @Context AccountService service) throws ServerException {
        service.checkPassword(account, request.getOldPassword());
        service.setPassword(account, request.getNewPassword());
    }

    InfoAdministratorDtoResponse accountToDtoInfo(Account account);
}
