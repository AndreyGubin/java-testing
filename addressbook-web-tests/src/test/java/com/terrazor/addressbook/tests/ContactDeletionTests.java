package com.terrazor.addressbook.tests;

import com.terrazor.addressbook.model.ContactData;
import com.terrazor.addressbook.model.Contacts;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.getContactHelper().goToHomePage();
        if (app.getContactHelper().getContactList().size() == 0) {
            app.getContactHelper().createContact(new ContactData().withFirstName("Andrey"), true);
        }
    }

    @Test
    public void testContactDeletion() {

        Contacts before = app.getContactHelper().all();
        ContactData deletedContact = before.iterator().next();
        app.getContactHelper().delete(deletedContact);
        Contacts after = app.getContactHelper().all();
        assertEquals(after.size(), before.size() - 1);
        assertThat(after, equalTo(before.without(deletedContact)));
        }
    }