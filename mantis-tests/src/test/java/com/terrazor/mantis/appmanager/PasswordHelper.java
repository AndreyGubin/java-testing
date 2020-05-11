package com.terrazor.mantis.appmanager;

import com.terrazor.mantis.model.MailMessage;
import com.terrazor.mantis.model.Users;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.lanwen.verbalregex.VerbalExpression;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PasswordHelper extends HelperBase {

    public PasswordHelper(ApplicationManager app) {
        super(app);
        wd = app.getDriver();
    }

    public void loginAsAdmin() {
        // авторизуемся под администратором
        type(By.name("username"), "administrator");
        click(By.cssSelector("input[type='submit']"));
        type(By.name("password"), "root");
        click(By.cssSelector("input[type='submit']"));
    }

    public void openUsersList() {
        // переходим на страницу пользователей
        wd.get(app.getProperty("web.baseUrl") + "/manage_user_page.php");
    }

    public Users changeUserPassword() throws IOException, MessagingException {

        //выбираем список пользователей
        List<Users> users = new ArrayList<Users>();
        users = getUserList();

        //выбираем пользователя
        Users selectContact = users.iterator().next();
        String selectName = selectContact.getUser();
        click(By.linkText(selectName));

        //нажимаем кнопку сбросить пароль
        click(By.cssSelector("input[value='Reset Password']"));

        //Проверяем почту, достаем ссылку и меняем пароль
        List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
        String confirmationLink = findConfirmationLink(mailMessages, selectContact.getEmail());
        app.registration().finish(confirmationLink, "12345");

        //авторизация с новым паролем
        HttpSession session = app.newSession();
        Users userTest = new Users();
        userTest.setUser(selectName).setPassword("12345");
        return userTest;
    }

    // достаем из почты ссылку на подтверждение смены пароля
    private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    private List<Users> getUserList() {
        List<Users> users = new ArrayList<Users>();

        List<WebElement> elements = wd.findElements(By.cssSelector("table.table-striped > tbody >tr"));
        for (WebElement element : elements) {//перебор строки
            List<WebElement> cells = element.findElements(By.cssSelector("td"));
            String username = cells.get(0).getText();
            String email = cells.get(2).getText();
            //проверяем, что не ломаем админа
            if (!(username.equals("administrator"))) {
                Users user = new Users().setUser(username).setEmail(email);
                users.add(user);
            }
        }
        return users;
    }

}