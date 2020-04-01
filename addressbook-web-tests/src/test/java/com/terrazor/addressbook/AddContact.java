package com.terrazor.addressbook;

import com.terrazor.addressbook.tests.TestBase;
import org.testng.annotations.*;
import org.openqa.selenium.*;

public class AddContact extends TestBase {
  private WebDriver wd;



  @Test
  public void testAddContact() throws Exception {
    gotoAddContactPage();
    fillContactInfoForm(new ContactInfo("Andrey", "Test", "Russia"));
    submitContactCreation();
    logout();
  }

  private void logout() {
    wd.findElement(By.linkText("Logout")).click();
  }

  private void submitContactCreation() {
    wd.findElement(By.xpath("(//input[@name='submit'])[2]")).click();
  }

  private void fillContactInfoForm(ContactInfo contactInfo) {
    wd.findElement(By.name("firstname")).click();
    wd.findElement(By.name("firstname")).clear();
    wd.findElement(By.name("firstname")).sendKeys(contactInfo.getFirstName());
    wd.findElement(By.name("lastname")).click();
    wd.findElement(By.name("lastname")).clear();
    wd.findElement(By.name("lastname")).sendKeys(contactInfo.getSecondName());
    wd.findElement(By.name("address")).click();
    wd.findElement(By.name("address")).clear();
    wd.findElement(By.name("address")).sendKeys(contactInfo.getAddress());
  }

  private void gotoAddContactPage() {
    wd.findElement(By.linkText("add new")).click();
  }



  private boolean isElementPresent(By by) {
    try {
      wd.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }


}
