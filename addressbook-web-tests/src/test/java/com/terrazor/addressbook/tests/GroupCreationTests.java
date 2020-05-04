package com.terrazor.addressbook.tests;

import com.terrazor.addressbook.model.GroupData;
import com.terrazor.addressbook.model.Groups;
import org.testng.annotations.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase{

    @Test
  public void testGroupCreation() {
        app.goTo().groupPage();
        Groups before = app.group().all();
        GroupData group = new GroupData().withName("test2");
        app.group().create(group);
        assertThat(app.group().getGroupCount(), equalTo(before.size() + 1));
        Groups after = app.group().all();
        assertThat(after, equalTo(
                before.withAdded(group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
    }

    @Test
    public void testBadGroupCreation() {
        app.goTo().groupPage();
        Groups before = app.group().all();
        GroupData group = new GroupData().withName("test'");
        app.group().create(group);
        assertThat(app.group().getGroupCount(), equalTo(before.size()));
        Groups after = app.group().all();
        assertThat(after, equalTo(before));}
}
