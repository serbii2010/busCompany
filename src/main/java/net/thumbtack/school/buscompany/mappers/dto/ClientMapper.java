package net.thumbtack.school.buscompany.mappers.dto;

import net.thumbtack.school.buscompany.dto.request.account.EditClientDtoRequest;
import net.thumbtack.school.buscompany.dto.request.account.RegistrationClientDtoRequest;
import net.thumbtack.school.buscompany.dto.response.account.EditClientDtoResponse;
import net.thumbtack.school.buscompany.dto.response.account.InfoClientDtoResponse;
import net.thumbtack.school.buscompany.dto.response.account.RegistrationClientDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.Account;
import net.thumbtack.school.buscompany.service.AccountService;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", imports = {AccountService.class}, uses = {AccountService.class})
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    Account registrationDtoToAccount(RegistrationClientDtoRequest request);

    RegistrationClientDtoResponse accountToDto(Account account);

    EditClientDtoResponse accountEditToDto(Account account);

    void update(@MappingTarget Account account, EditClientDtoRequest request, @Context AccountService service) throws ServerException;

    @AfterMapping
    default void setPassword(@MappingTarget Account account, EditClientDtoRequest request, @Context AccountService service) throws ServerException {
        service.checkPassword(account, request.getOldPassword());
        service.setPassword(account, request.getNewPassword());
    }

    InfoClientDtoResponse accountToDtoInfo(Account account);
}
