package net.thumbtack.school.buscompany.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.thumbtack.school.buscompany.dto.response.SettingsDtoResponse;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SettingsHelper {
    private SettingsDtoResponse settingsDtoResponse;

    public void init() {
        settingsDtoResponse = new SettingsDtoResponse("50", "8");
    }
}
