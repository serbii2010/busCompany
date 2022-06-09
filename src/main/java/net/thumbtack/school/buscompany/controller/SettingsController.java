package net.thumbtack.school.buscompany.controller;

import net.thumbtack.school.buscompany.dto.response.SettingsDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/settings")
public class SettingsController {
    @Autowired
    private AccountService accountService;

    @Value("${max_name_length}")
    private String maxNameLength;
    @Value("${min_password_length}")
    private String minPasswordLength;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public SettingsDtoResponse getSettings(@CookieValue(value = "JAVASESSIONID", required = false) String javaSessionId) throws ServerException {
        if (javaSessionId == null) {
            return new SettingsDtoResponse(maxNameLength, minPasswordLength);
        }
        if (accountService.isClient(javaSessionId)) {
            return new SettingsDtoResponse(maxNameLength, minPasswordLength);
        } else if (accountService.isAdmin(javaSessionId)) {
            return new SettingsDtoResponse(maxNameLength, minPasswordLength);
        }
        return new SettingsDtoResponse(maxNameLength, minPasswordLength);
    }
}
