package com.terrazor.addressbook.tests;

import com.terrazor.addressbook.model.ContactData;
import com.terrazor.addressbook.model.Contacts;
import org.testng.annotations.Test;

import java.io.File;
import java.sql.SQLOutput;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() {
        app.getContactHelper().goToHomePage();
        Contacts before = app.getContactHelper().all();
        File photo = new File("src/test/java/com/terrazor/addressbook/resources/pic.jpg");
        ContactData contact = new ContactData().withFirstName("Andrey").withLastName("Gubin").withPhoto(photo).withGroup("test1").withHomePhone("123").withMobilePhone("456").withWorkPhone("789");
        app.getContactHelper().createContact(contact, true);
        Contacts after = app.getContactHelper().all();
        assertThat(after.size(), equalTo(before.size() + 1));
        contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt());
        assertThat(after, equalTo(before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
    }

    @Test (enabled = false)
    public void testCurrentDir() {
        File currentDir = new File(".");
        System.out.println(currentDir.getAbsolutePath());
        File photo = new File("src/test/java/com/terrazor/addressbook/resources/pic.jpg");
        System.out.println(photo.getAbsolutePath());
        System.out.println(photo.exists());
    }
}