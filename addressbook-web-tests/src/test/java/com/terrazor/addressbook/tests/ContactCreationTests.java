package com.terrazor.addressbook.tests;

import com.terrazor.addressbook.model.ContactData;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Set;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {
    app.getContactHelper().goToHomePage();
    Set<ContactData> before = app.getContactHelper().all();
    ContactData contact = new ContactData().withFirstName("Andrey").withLastName("Gubin").withMobileNumber("+7927000000").withEmail("test@mail.ru").withGroup("test1");
    app.getContactHelper().createContact(contact, true);
    Set<ContactData> after = app.getContactHelper().all();
    Assert.assertEquals(after.size(), before.size() + 1);

    contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt());
    before.add(contact);
    Assert.assertEquals(before, after);
  }
}