package com.terrazor.addressbook.tests;

import com.terrazor.addressbook.model.ContactData;
import com.terrazor.addressbook.model.GroupData;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;

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

        Set<ContactData> before = app.getContactHelper().all();
        ContactData deletedContact = before.iterator().next();
        app.getContactHelper().delete(deletedContact);
        Set<ContactData> after = app.getContactHelper().all();
        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(deletedContact);
        Assert.assertEquals(before, after);
        }
    }