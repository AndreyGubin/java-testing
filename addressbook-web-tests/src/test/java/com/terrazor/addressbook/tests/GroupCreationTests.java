package com.terrazor.addressbook.tests;

import com.terrazor.addressbook.model.GroupData;
import org.testng.annotations.*;

public class GroupCreationTests extends TestBase{

    @Test
  public void testGroupCreation() throws Exception {
    app.gotoGroupPage();
    app.getGroupHelper().initGroupCreation();
    app.getGroupHelper().fillGroupForm(new GroupData("test1", "test2", "test3"));
    app.getGroupHelper().submitGroupCreation();
    app.getGroupHelper().returnToGroupPage();
  }

}
