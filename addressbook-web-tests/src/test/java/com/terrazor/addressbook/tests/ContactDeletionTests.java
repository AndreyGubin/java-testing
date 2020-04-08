package com.terrazor.addressbook.tests;

import com.terrazor.addressbook.model.ContactData;
import com.terrazor.addressbook.model.GroupData;
import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion() throws Exception {
        if (! app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData("Andrey", "Gubin", "+79991234567", "test@mail.ru", "test1"), true);
        }
        app.getContactHelper().selectContact();
        app.getContactHelper().deleteContact();
        app.getContactHelper().acceptDeletion();
    }
}
