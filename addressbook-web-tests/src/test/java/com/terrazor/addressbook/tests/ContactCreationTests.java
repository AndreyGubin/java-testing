package com.terrazor.addressbook.tests;

import com.terrazor.addressbook.model.ContactData;
import com.terrazor.addressbook.model.Contacts;
import org.testng.annotations.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {
    app.getContactHelper().goToHomePage();
    Contacts before = app.getContactHelper().all();
    ContactData contact = new ContactData().withFirstName("Andrey").withLastName("Gubin").withMobileNumber("+7927000000").withEmail("test@mail.ru").withGroup("test1");
    app.getContactHelper().createContact(contact, true);
    Contacts after = app.getContactHelper().all();
    assertThat(after.size(), equalTo(before.size() + 1));
    contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt());
    assertThat(after, equalTo(before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
  }
}