package net.thumbtack.school.buscompany.mappers.dto.account;

import net.thumbtack.school.buscompany.dto.request.account.EditClientDtoRequest;
import net.thumbtack.school.buscompany.dto.request.account.RegistrationClientDtoRequest;
import net.thumbtack.school.buscompany.dto.response.account.EditClientDtoResponse;
import net.thumbtack.school.buscompany.dto.response.account.InfoClientDtoResponse;
import net.thumbtack.school.buscompany.dto.response.account.RegistrationClientDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.account.Account;
import net.thumbtack.school.buscompany.model.account.Client;
import net.thumbtack.school.buscompany.service.account.AccountService;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", imports = {AccountService.class}, uses = {AccountService.class})
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    @Mapping(target = "phone", expression = "java(request.getPhone().replaceAll(\"-\", \"\"))")
    Client registrationDtoToAccount(RegistrationClientDtoRequest request);

    @Mapping(target = "userType", source = "userType.type")
    RegistrationClientDtoResponse accountToDto(Client account);

    @Mapping(target = "userType", source = "userType.type")
    EditClientDtoResponse accountEditToDto(Client account);

    @Mapping(target = "phone", expression = "java(request.getPhone().replaceAll(\"-\", \"\"))")
    void update(@MappingTarget Client account, EditClientDtoRequest request, @Context AccountService service) throws ServerException;

    @AfterMapping
    default void setPassword(@MappingTarget Client account, EditClientDtoRequest request, @Context AccountService service) throws ServerException {
        service.checkPassword(account, request.getOldPassword());
        service.setPassword(account, request.getNewPassword());
    }

    @Mapping(target = "userType", source = "userType.type")
    InfoClientDtoResponse accountToDtoInfo(Client account);
}
