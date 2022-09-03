package net.thumbtack.school.buscompany.service;

import net.thumbtack.school.buscompany.dto.response.SettingsDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.helper.AccountHelper;
import net.thumbtack.school.buscompany.helper.SettingsHelper;
import net.thumbtack.school.buscompany.service.account.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("dev")
public class TestSettingsService {
    @Autowired
    private SettingsHelper settingsHelper;
    @Autowired
    private AccountHelper accountHelper;

    @Mock
    private AccountService accountService;
    @InjectMocks
    private SettingsService settingsService;

    private SettingsDtoResponse response;

    @BeforeEach
    public void init() {
        settingsHelper.init();
        response = settingsHelper.getSettingsDtoResponse();
        ReflectionTestUtils.setField(settingsService, "maxNameLength", "50");
        ReflectionTestUtils.setField(settingsService, "minPasswordLength", "8");
    }

    @Test
    public void getSettings_admin() throws Exception {
        Mockito.when(accountService.getAuthAccount("123")).thenReturn(accountHelper.getAdmin());
        Mockito.when(accountService.isAdmin(accountHelper.getAdmin())).thenReturn(true);
        SettingsDtoResponse getResponse = settingsService.getSettings("123");
        assertEquals(response, getResponse);
    }

    @Test
    public void getSettings_user() throws Exception {
        Mockito.when(accountService.getAuthAccount("123")).thenReturn(accountHelper.getClient());
        Mockito.when(accountService.isAdmin(accountHelper.getClient())).thenReturn(true);
        SettingsDtoResponse getResponse = settingsService.getSettings("123");
        assertEquals(response, getResponse);
    }

    @Test
    public void getSettings_notAuth() throws Exception {
        SettingsDtoResponse getResponse = settingsService.getSettings(null);
        assertEquals(response, getResponse);
    }

    @Test
    public void getSettings_badCookie() throws Exception {
        Mockito.when(accountService.getAuthAccount("123")).thenThrow(new ServerException(ServerErrorCode.USER_NOT_AUTHORIZATION));
        assertThrows(ServerException.class, () -> settingsService.getSettings("123"));
    }

}
