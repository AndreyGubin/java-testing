package com.terrazor.addressbook.tests;

import com.terrazor.addressbook.appmanager.ApplicationManager;
import com.terrazor.addressbook.model.ContactData;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

public class TestBase {
    private WebDriver wd;

    protected final ApplicationManager app = new ApplicationManager(BrowserType.CHROME);

    @BeforeMethod(alwaysRun = true)
    public void setUp() throws Exception {
        app.init();

    }




    private boolean isElementPresent(By by) {
        try {
            wd.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            wd.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    protected void returnToHomePage() {
        wd.findElement(By.linkText("home page")).click();
    }

    protected void submitContactCreation() {
        wd.findElement(By.xpath("(//input[@name='submit'])[2]")).click();
    }

    protected void fillContactPage(ContactData contactData) {
        wd.findElement(By.name("firstname")).click();
        wd.findElement(By.name("firstname")).clear();
        wd.findElement(By.name("firstname")).sendKeys(contactData.getFirstName());
        wd.findElement(By.name("lastname")).click();
        wd.findElement(By.name("lastname")).clear();
        wd.findElement(By.name("lastname")).sendKeys(contactData.getLastName());
        wd.findElement(By.name("mobile")).click();
        wd.findElement(By.name("mobile")).clear();
        wd.findElement(By.name("mobile")).sendKeys(contactData.getMobileNumber());
        wd.findElement(By.name("email")).click();
        wd.findElement(By.name("email")).clear();
        wd.findElement(By.name("email")).sendKeys(contactData.getEmail());
    }

    protected void gotoAddContactPage() {
        wd.findElement(By.linkText("add new")).click();
    }

    protected void selectContact() {
        wd.findElement(By.name("selected[]")).click();
    }

    protected void deleteContact() {
        wd.findElement(By.xpath("//input[@value='Delete']")).click();
    }

    protected void acceptDeletion() {
        wd.switchTo().alert().accept();
    }

    protected void editContact() {
        wd.findElement(By.xpath("//img[@alt='Edit']")).click();
    }

    protected void saveContactEdition() {
        wd.findElement(By.name("update")).click();
    }

    protected void editContactAddress() {
        wd.findElement(By.name("address")).click();
        wd.findElement(By.name("address")).clear();
        wd.findElement(By.name("address")).sendKeys("Russia");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() throws Exception {
        wd.quit();
    }

}
