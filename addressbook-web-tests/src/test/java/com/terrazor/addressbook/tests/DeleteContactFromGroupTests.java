package com.terrazor.addressbook.tests;

import com.terrazor.addressbook.model.ContactData;
import com.terrazor.addressbook.model.Contacts;
import com.terrazor.addressbook.model.GroupData;
import com.terrazor.addressbook.model.Groups;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class DeleteContactFromGroupTests extends TestBase {

    public GroupData selectedGroup;
    private ContactData selectedContact;

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().size() == 0) {
            app.getContactHelper().goToHomePage();
            app.getContactHelper().createContact(new ContactData().withFirstName("Andrey").withLastName("Gubin").withHomePhone("12345").withMobilePhone("33333").withWorkPhone("55555"), true);
        }

        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
        }

        Contacts contactSet = app.db().contacts();
        Groups groupSet = app.db().groups();
        for (ContactData contact : contactSet)
        {
            for (GroupData group : groupSet) {
                if (groupSet.stream().filter(g -> g.getName().equals(group.getName())).collect(Collectors.toList()).size() > 1) {
                    continue;
                }
                if (contact.getGroups().contains(group)) {
                    selectedContact = contact;
                    selectedGroup = group;
                    return;
                }
            }
        }
        if ((selectedContact == null) && (selectedGroup == null)) {
            selectedContact = new ContactData().withFirstName("Andrey").withLastName("Gubin");
            selectedGroup = new GroupData().withName(String.format("Test (%s)", Math.round(Math.random() * 10)));
            app.getContactHelper().createContact(new ContactData().withFirstName("Andrey").withLastName("Gubin").withHomePhone("12345").withMobilePhone("33333").withWorkPhone("55555"), true);
            app.goTo().groupPage();
            app.group().create(selectedGroup);

            contactSet = app.db().contacts();
            groupSet = app.db().groups();
            selectedContact.withId(contactSet.stream().mapToInt((c) ->c.getId()).max().getAsInt());
            selectedGroup.withId(groupSet.stream().mapToInt((g) ->g.getId()).max().getAsInt());

            app.getContactHelper().addContactToGroup(selectedContact, selectedGroup);

        }
    }

    @Test(enabled = false)
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

    // исправление ошибок
    @Test
    public void testContactRemoveGroup2() {
        app.getContactHelper().deleteContactFromGroup(selectedContact,selectedGroup);
        ContactData contactFromDb = app.db().selectContact(selectedContact.getId());
        assertThat(contactFromDb.getGroups(), not(hasItem(selectedGroup)));
    }
}