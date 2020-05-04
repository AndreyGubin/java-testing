package com.terrazor.addressbook.tests;

import com.terrazor.addressbook.model.ContactData;
import com.terrazor.addressbook.model.Contacts;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.getContactHelper().goToHomePage();
        if (app.getContactHelper().getContactList().size() == 0) {
            app.getContactHelper().createContact(new ContactData().withFirstName("Andrey").withLastName("Gubin"), true);
        }
    }

    @Test
    public void testContactModification() {
        Contacts before = app.getContactHelper().all();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData().withId(modifiedContact.getId()).withFirstName("Andrey").withLastName("Gubin").withMobileNumber("+7927000000").withEmail("test@mail.ru").withEmail2("test2@mail.ru").withEmail3("test3@mail.ru").withGroup("test").withWorkPhone("777").withMobilePhone("8787").withHomePhone("454");
        app.getContactHelper().modify(contact);
        Contacts after = app.getContactHelper().all();
        assertEquals(after.size(), before.size());
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    }
}