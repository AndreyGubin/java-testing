package com.terrazor.addressbook.tests;

import com.terrazor.addressbook.model.ContactData;
import org.testng.annotations.Test;

public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification() throws Exception {
        app.getContactHelper().editContact();
        app.getContactHelper().fillContactPage(new ContactData("test_name", "test_lastname", "test_mobile", "test_email", null), false);
        app.getContactHelper().saveContactEdition();
    }
}
