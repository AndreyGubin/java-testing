package com.terrazor.addressbook.tests;

import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBaseContact {

    @Test
    public void testContactDeletion() throws Exception {
        selectContact();
        deleteContact();
        acceptDeletion();
    }
}
