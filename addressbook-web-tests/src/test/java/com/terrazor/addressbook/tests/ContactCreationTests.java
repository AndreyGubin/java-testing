package com.terrazor.addressbook.tests;

import com.terrazor.addressbook.model.ContactData;
import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    gotoAddContactPage();
    fillContactPage(new ContactData("Andrey", "Gubin", "+79991234567", "test@mail.ru"));
    submitContactCreation();
    returnToHomePage();
  }
}
