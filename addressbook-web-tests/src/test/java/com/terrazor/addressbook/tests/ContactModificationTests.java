package com.terrazor.addressbook.tests;

import org.testng.annotations.Test;

public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification() throws Exception {
        editContact();
        editContactAddress();
        saveContactEdition();
    }
}
