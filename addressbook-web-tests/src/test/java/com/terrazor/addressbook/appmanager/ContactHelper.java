package com.terrazor.addressbook.appmanager;

import com.terrazor.addressbook.model.ContactData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ContactHelper extends BaseHelper {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }


    public void gotoAddContactPage() {
        wd.findElement(By.linkText("add new")).click();
    }

    public void selectContact() {
        wd.findElement(By.name("selected[]")).click();
    }

    public void deleteContact() {
        wd.findElement(By.xpath("//input[@value='Delete']")).click();
    }

    public void acceptDeletion() {
        wd.switchTo().alert().accept();
    }

    public void editContact() {
        wd.findElement(By.xpath("//img[@alt='Edit']")).click();
    }

    public void saveContactEdition() {
        wd.findElement(By.name("update")).click();
    }

    public void editContactAddress() {
        wd.findElement(By.name("address")).click();
        wd.findElement(By.name("address")).clear();
        wd.findElement(By.name("address")).sendKeys("Russia");
    }

    public void returnToHomePage() {
        wd.findElement(By.linkText("home page")).click();
    }

    public void submitContactCreation() {
        wd.findElement(By.xpath("(//input[@name='submit'])[2]")).click();
    }

    public void fillContactPage(ContactData contactData) {
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

}
