package net.thumbtack.school.buscompany.mappers.dto.account;

import net.thumbtack.school.buscompany.dto.request.account.EditAdministratorDtoRequest;
import net.thumbtack.school.buscompany.dto.request.account.RegistrationAdminDtoRequest;
import net.thumbtack.school.buscompany.dto.response.account.EditAdministratorDtoResponse;
import net.thumbtack.school.buscompany.dto.response.account.InfoAdministratorDtoResponse;
import net.thumbtack.school.buscompany.dto.response.account.RegistrationAdminDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.Account;
import net.thumbtack.school.buscompany.service.AccountService;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    AdminMapper INSTANCE = Mappers.getMapper(AdminMapper.class);

    Account registrationAdminDtoToAccount(RegistrationAdminDtoRequest response);

    @Mapping(target = "userType", source = "userType.type")
    RegistrationAdminDtoResponse accountToDto(Account account);

    @Mapping(target = "userType", source = "userType.type")
    EditAdministratorDtoResponse accountEditToDto(Account account);

    void update(@MappingTarget Account account, EditAdministratorDtoRequest request, @Context AccountService service) throws ServerException;

    @AfterMapping
    default void setPassword(@MappingTarget Account account, EditAdministratorDtoRequest request, @Context AccountService service) throws ServerException {
        service.checkPassword(account, request.getOldPassword());
        service.setPassword(account, request.getNewPassword());
    }

    @Mapping(target = "userType", source = "userType.type")
    InfoAdministratorDtoResponse accountToDtoInfo(Account account);
}
