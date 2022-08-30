package net.thumbtack.school.buscompany.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.thumbtack.school.buscompany.model.account.Account;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Session {
    int id;
    Account account;
    String sessionId;
    LocalDateTime lastAction;

    public Session(Account account) {
        this(0, account, UUID.randomUUID().toString(), LocalDateTime.now());
    }
}
