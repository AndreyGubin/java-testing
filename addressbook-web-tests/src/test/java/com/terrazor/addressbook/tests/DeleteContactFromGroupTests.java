package com.terrazor.addressbook.tests;

import com.terrazor.addressbook.model.ContactData;
import com.terrazor.addressbook.model.GroupData;
import com.terrazor.addressbook.model.Groups;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DeleteContactFromGroupTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().size() == 0) {
            app.getContactHelper().goToHomePage();
            app.getContactHelper().createContact(new ContactData().withFirstName("Andrey").withLastName("Gubin").withHomePhone("12345"), true);
        }

        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
        }
    }

    @Test
    public void testContactRemoveGroup() {

        GroupData selectedGroup = app.db().groups().iterator().next();
        ContactData modifiedContact = app.db().contacts().iterator().next();

        if (!selectedGroup.getContacts().contains(modifiedContact)) {
            app.getContactHelper().addContactToGroup(modifiedContact, selectedGroup);
        }
        app.getContactHelper().goToHomePage();
        app.getContactHelper().deleteContactFromGroup(modifiedContact, selectedGroup);
        Groups allGroupsForContactAfter = app.db().contacts().stream().filter((c) -> c.getId() == modifiedContact.getId()).findFirst().get().getGroups();
        Assert.assertFalse(allGroupsForContactAfter.contains(selectedGroup));
    }
}