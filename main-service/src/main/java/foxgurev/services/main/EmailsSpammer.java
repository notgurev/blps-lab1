package foxgurev.services.main;

import foxgurev.services.main.auth.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class EmailsSpammer {
    private final UserService userService;

    @Scheduled(fixedRate = 20_000) // для демонстрации
//    @Scheduled(cron = "0 12 * * *") // каждый день в 12:00
    public void spamEmails() {
        String s = userService.getAllEmails().toString();
        log.info("Рассылаю назойливые сообщения всем пользователям...");
        log.info("Эти счастливчики получили электронные письма о наших крутых скидках: " + s);
    }
}
