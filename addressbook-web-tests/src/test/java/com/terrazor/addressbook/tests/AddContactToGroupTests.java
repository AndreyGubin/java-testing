package com.terrazor.addressbook.tests;

import com.terrazor.addressbook.model.ContactData;
import com.terrazor.addressbook.model.GroupData;
import com.terrazor.addressbook.model.Groups;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class AddContactToGroupTests extends TestBase {

    public GroupData selectedGroup;

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
}
