package net.thumbtack.school.bascompany.mapper;

import net.thumbtack.school.bascompany.dto.request.account.RegistrationAdminDtoRequest;
import net.thumbtack.school.bascompany.dto.response.account.RegistrationAdminDtoResponse;
import net.thumbtack.school.bascompany.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    AdminMapper INSTANCE = Mappers.getMapper(AdminMapper.class);

    Account registrationAdminDtoToAccount(RegistrationAdminDtoRequest response);

    RegistrationAdminDtoResponse accountToDto(Account account);
}
