package net.thumbtack.school.buscompany.mappers.dto.account;

import net.thumbtack.school.buscompany.dto.request.account.EditAdministratorDtoRequest;
import net.thumbtack.school.buscompany.dto.request.account.RegistrationAdminDtoRequest;
import net.thumbtack.school.buscompany.dto.response.account.EditAdministratorDtoResponse;
import net.thumbtack.school.buscompany.dto.response.account.InfoAdministratorDtoResponse;
import net.thumbtack.school.buscompany.dto.response.account.RegistrationAdminDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.account.Account;
import net.thumbtack.school.buscompany.model.account.Admin;
import net.thumbtack.school.buscompany.service.account.AccountService;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    AdminMapper INSTANCE = Mappers.getMapper(AdminMapper.class);

    Admin registrationAdminDtoToAccount(RegistrationAdminDtoRequest response);

    @Mapping(target = "userType", source = "userType.type")
    RegistrationAdminDtoResponse accountToDto(Admin account);

    @Mapping(target = "userType", source = "userType.type")
    EditAdministratorDtoResponse accountEditToDto(Admin account);

    void update(@MappingTarget Admin admin, EditAdministratorDtoRequest request, @Context AccountService service) throws ServerException;

    @AfterMapping
    default void setPassword(@MappingTarget Account account, EditAdministratorDtoRequest request, @Context AccountService service) throws ServerException {
        service.checkPassword(account, request.getOldPassword());
        service.setPassword(account, request.getNewPassword());
    }

    @Mapping(target = "userType", source = "userType.type")
    InfoAdministratorDtoResponse accountToDtoInfo(Admin account);
}
