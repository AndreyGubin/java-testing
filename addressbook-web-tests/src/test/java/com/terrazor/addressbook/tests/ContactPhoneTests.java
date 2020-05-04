package com.terrazor.addressbook.tests;

import com.terrazor.addressbook.model.ContactData;
import org.testng.annotations.Test;

public class ContactPhoneTests extends TestBase{

    @Test
    public void testContactPhones() {
        app.getContactHelper().goToHomePage();
        ContactData contact = app.getContactHelper().all().iterator().next();
        ContactData contactInfoFromEditForm = app.getContactHelper().infoFromEditForm(contact);
    }
}
