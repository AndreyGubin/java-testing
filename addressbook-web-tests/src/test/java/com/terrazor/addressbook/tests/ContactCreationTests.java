package com.terrazor.addressbook.tests;

import com.terrazor.addressbook.model.ContactData;
import com.terrazor.addressbook.model.GroupData;
import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    app.getContactHelper().createContact(new ContactData("Andrey", "Gubin", "+79991234567", "test@mail.ru", "test1"), true);
  }
}
