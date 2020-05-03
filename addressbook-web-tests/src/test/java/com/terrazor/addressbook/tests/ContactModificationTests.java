package com.terrazor.addressbook.tests;

import com.terrazor.addressbook.model.ContactData;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        Set<ContactData> before = app.getContactHelper().all();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData().withId(modifiedContact.getId()).withFirstName("Andrey").withLastName("Gubin").withMobileNumber("+7927000000").withEmail("test@mail.ru").withGroup("test");
        app.getContactHelper().modify(contact);
        Set<ContactData> after = app.getContactHelper().all();
        Assert.assertEquals(after.size(), before.size());

        before.remove(modifiedContact);
        before.add(contact);
        Assert.assertEquals(before, after);

    }


}