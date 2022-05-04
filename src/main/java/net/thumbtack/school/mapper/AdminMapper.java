package net.thumbtack.school.mapper;

import net.thumbtack.school.dto.request.user.RegistrationAdminDtoRequest;
import net.thumbtack.school.dto.response.user.RegistrationAdminDtoResponse;
import net.thumbtack.school.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    AdminMapper INSTANCE = Mappers.getMapper(AdminMapper.class);

    Account registrationAdminDtoToAccount(RegistrationAdminDtoRequest response);

    RegistrationAdminDtoResponse accountToDto(Account account);
}
