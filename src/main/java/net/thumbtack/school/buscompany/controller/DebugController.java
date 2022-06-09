package net.thumbtack.school.buscompany.controller;

import net.thumbtack.school.buscompany.service.DebugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/debug")
@Profile("dev")
public class DebugController {
    @Autowired
    private DebugService debugService;

    @PostMapping(path = "/clear")
    public void clear() {
        debugService.clear();
    }
}
