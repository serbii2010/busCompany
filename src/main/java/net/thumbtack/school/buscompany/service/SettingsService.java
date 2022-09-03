package net.thumbtack.school.buscompany.service;

import net.thumbtack.school.buscompany.dto.response.SettingsDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.account.Account;
import net.thumbtack.school.buscompany.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {
    @Autowired
    private AccountService accountService;

    @Value("${max_name_length}")
    private String maxNameLength;
    @Value("${min_password_length}")
    private String minPasswordLength;

    public SettingsDtoResponse getSettings(String javaSessionId) throws ServerException {
        if (javaSessionId == null) {
            return new SettingsDtoResponse(maxNameLength, minPasswordLength);
        }
        Account account = accountService.getAuthAccount(javaSessionId);
        if (accountService.isClient(account)) {
            return new SettingsDtoResponse(maxNameLength, minPasswordLength);
        } else if (accountService.isAdmin(account)) {
            return new SettingsDtoResponse(maxNameLength, minPasswordLength);
        }
        return new SettingsDtoResponse(maxNameLength, minPasswordLength);
    }

}
