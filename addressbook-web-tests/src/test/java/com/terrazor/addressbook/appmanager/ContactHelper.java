package com.terrazor.addressbook.appmanager;

import com.terrazor.addressbook.model.ContactData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends BaseHelper {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void gotoAddContactPage() {
        wd.findElement(By.linkText("add new")).click();
    }

    public void goToHomePage() {
        wd.findElement(By.linkText("home")).click();
    }

    public void selectContact(int index) {
        wd.findElements(By.name("selected[]")).get(index).click();
    }

    public void deleteContact() {
        wd.findElement(By.xpath("//input[@value='Delete']")).click();
    }

    public void acceptDeletion() {
        wd.switchTo().alert().accept();
    }

    public void editContact(int index) {
        wd.findElements(By.xpath("//img[@alt='Edit']")).get(index).click();
    }

    public void saveContactEdition() {
        wd.findElement(By.name("update")).click();
    }

    public void editContactAddress() {
        wd.findElement(By.name("address")).click();
        wd.findElement(By.name("address")).clear();
        wd.findElement(By.name("address")).sendKeys("Russia");
    }

    public void submitContactCreation() {
        wd.findElement(By.xpath("(//input[@name='submit'])[2]")).click();
    }

    public void fillContactPage(ContactData contactData, boolean creation) {
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

        if (creation) {
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void createContact(ContactData contactData, boolean creation) {
        gotoAddContactPage();
        fillContactPage(new ContactData("Andrey", "Gubin", "+79991234567", "test@mail.ru", "test1"), true);
        submitContactCreation();
        goToHomePage();
    }


    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public int getContactCount() {
        return wd.findElements(By.name("selected[]")).size();
    }

    public List<ContactData> getContactList() {
        List<ContactData> contacts = new ArrayList<ContactData>();
        List<WebElement> elements = wd.findElements(By.cssSelector("[name='entry']"));
        for (WebElement element : elements) {
            String fname = element.findElement(By.xpath("td[3]")).getText();
            String lname = element.findElement(By.xpath("td[2]")).getText();
            int id = Integer.parseInt(element.findElement(By.xpath("//input[@type='checkbox'and@name='selected[]']")).getAttribute("value"));
            ContactData contact = new ContactData(id, fname, lname, null, null, null);
            contacts.add(contact);
        }
        return contacts;
    }
}
