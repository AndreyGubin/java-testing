package com.terrazor.mantis.tests;

import com.terrazor.mantis.model.Users;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.mail.MessagingException;
import java.io.IOException;

import static com.terrazor.mantis.tests.TestBase.app;

public class ChangePasswordTests {

    @BeforeMethod
    public void startMailServer() {
        app.mail().start();
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }

    @Test
    public void testResetPassword() throws IOException, MessagingException {
        app.passwordHelper().loginAsAdmin();
        app.passwordHelper().openUsersList();
        Users userTest = new Users();
        userTest = app.passwordHelper().changeUserPassword();
        Assert.assertTrue(app.newSession().login(userTest.getUser(), userTest.getPassword()));
    }
}
