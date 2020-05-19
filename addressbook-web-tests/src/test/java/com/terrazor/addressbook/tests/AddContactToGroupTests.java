package com.terrazor.addressbook.tests;

import com.terrazor.addressbook.model.ContactData;
import com.terrazor.addressbook.model.Contacts;
import com.terrazor.addressbook.model.GroupData;
import com.terrazor.addressbook.model.Groups;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;

public class AddContactToGroupTests extends TestBase {

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
                if (!contact.getGroups().contains(group)) {
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
        }
    }

    @Test(enabled = false)
    public void testContactAddGroup() {
        Groups groups = app.db().groups();
        selectedGroup = groups.iterator().next();
        ContactData modifiedContact = app.db().contacts().iterator().next();
        Groups allGroupsForContactBefore = modifiedContact.getGroups();

        if (allGroupsForContactBefore.contains(selectedGroup)) {
            if (allGroupsForContactBefore.equals(groups)) {
                app.goTo().groupPage();
                GroupData newGroup = new GroupData().withName("test").withHeader("header").withFooter("footer");
                app.group().create(newGroup);
                selectedGroup = newGroup.withId(app.db().groups().stream().mapToInt((g) -> g.getId()).max().getAsInt());
                app.getContactHelper().goToHomePage();
            } else {
                selectedGroup = groups.stream().filter((g) -> !allGroupsForContactBefore.contains(g)).findFirst().get();
            }
        }
        app.getContactHelper().addContactToGroup(modifiedContact, selectedGroup);
        Groups allGroupsForContactAfter = app.db().contacts().stream().filter((c) -> c.getId() == modifiedContact.getId()).findFirst().get().getGroups();
        assertThat(allGroupsForContactAfter.size(), equalTo(allGroupsForContactBefore.size() + 1));
        Assert.assertTrue(allGroupsForContactAfter.contains(selectedGroup));
    }

    // исправление ошибок
    @Test
    public void testContactAddGroup2() {
        app.getContactHelper().addContactToGroup(selectedContact, selectedGroup);
        ContactData contactFromDb = app.db().selectContact(selectedContact.getId());
        assertThat(contactFromDb.getGroups(), hasItem(selectedGroup));
    }
}