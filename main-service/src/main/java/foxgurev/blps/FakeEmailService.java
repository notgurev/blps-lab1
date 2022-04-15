package foxgurev.blps;

public class FakeEmailService implements EmailService {
    @Override
    public void sendGreetingEmail() {
        /*
            Этот сервис ничего не делает. Он бесполезен, ничтожен, легко заменим.
            Все, на что он способен - отправить жалкое сообщение в лог, чтобы сымитировать реальное действие.
         */
    }
}
