package com.terrazor.addressbook.appmanager;

import com.terrazor.addressbook.model.ContactData;
import com.terrazor.addressbook.model.Contacts;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends BaseHelper {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void gotoAddContactPage() {
        wd.findElement(By.linkText("add new")).click();
    }

    public void goToHomePage() {
        wd.findElement(By.linkText("home")).click();
    }

    public void selectContact(int index) {
        wd.findElements(By.name("selected[]")).get(index).click();
    }

    public void selectContactById(int id) {
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    public void deleteContact() {
        wd.findElement(By.xpath("//input[@value='Delete']")).click();
    }

    public void acceptDeletion() {
        wd.switchTo().alert().accept();
    }

    public void editContact(int index) {
        wd.findElements(By.xpath("//img[@alt='Edit']")).get(index).click();
    }

    public void saveContactEdition() {
        wd.findElement(By.name("update")).click();
    }

    public void editContactAddress() {
        wd.findElement(By.name("address")).click();
        wd.findElement(By.name("address")).clear();
        wd.findElement(By.name("address")).sendKeys("Russia");
    }

    public void submitContactCreation() {
        wd.findElement(By.xpath("(//input[@name='submit'])[2]")).click();
    }

    public void fillContactPage(ContactData contactData, boolean creation) {
        wd.findElement(By.name("firstname")).click();
        wd.findElement(By.name("firstname")).clear();
        wd.findElement(By.name("firstname")).sendKeys(contactData.getFirstName());
        wd.findElement(By.name("lastname")).click();
        wd.findElement(By.name("lastname")).clear();
        wd.findElement(By.name("lastname")).sendKeys(contactData.getLastName());
        wd.findElement(By.name("home")).click();
        wd.findElement(By.name("home")).clear();
        wd.findElement(By.name("home")).sendKeys(contactData.getHomePhone());
        wd.findElement(By.name("mobile")).click();
        wd.findElement(By.name("mobile")).clear();
        wd.findElement(By.name("mobile")).sendKeys(contactData.getMobilePhone());
        wd.findElement(By.name("work")).click();
        wd.findElement(By.name("work")).clear();
        wd.findElement(By.name("work")).sendKeys(contactData.getWorkPhone());

        if (creation) {
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public ContactData infoFromEditForm(ContactData contact) {
        editContactById(contact.getId());
        String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        String address = wd.findElement(By.name("address")).getText();
        String email = wd.findElement(By.name("email")).getText();
        String email2 = wd.findElement(By.name("email2")).getText();
        String email3 = wd.findElement(By.name("email3")).getText();


        wd.navigate().back();
        return new ContactData().withId(contact.getId()).withFirstName(firstname)
                .withLastName(lastname).withAddress(address).withEmail(email).withEmail2(email2).withEmail3(email3).withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work);

    }

    private void initContactModificationById(int id) {
        WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value'%s']", id)));
        WebElement row = checkbox.findElement(By.xpath("./../.."));
        List<WebElement> cells = row.findElements(By.tagName("td"));
        cells.get(7).findElement(By.tagName("a")).click();
    }

    public void createContact(ContactData contactData, boolean creation) {
        gotoAddContactPage();
        fillContactPage(contactData, true);
        submitContactCreation();
        goToHomePage();
    }

    public void modify(ContactData contact) {
        editContactById(contact.getId());
        fillContactPage(contact, false);
        saveContactEdition();
        goToHomePage();
    }

    private void editContactById(int id) {
        wd.findElement(By.xpath("//a[@href=\"edit.php?id=" + id + "\"]")).click();
    }

    public void delete(int index) {
        selectContact(index);
        deleteContact();
        acceptDeletion();
        goToHomePage();
    }

    public void delete(ContactData deletedContact) {
        selectContactById(deletedContact.getId());
        deleteContact();
        acceptDeletion();
        goToHomePage();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public int getContactCount() {
        return wd.findElements(By.name("selected[]")).size();
    }

    public List<ContactData> getContactList() {
        List<ContactData> contacts = new ArrayList<ContactData>();
        List<WebElement> elements = wd.findElements(By.cssSelector("[name='entry']"));
        for (WebElement element : elements) {
            String fname = element.findElement(By.xpath("td[3]")).getText();
            String lname = element.findElement(By.xpath("td[2]")).getText();
            int id = Integer.parseInt(element.findElement(By.xpath("//input[@type='checkbox'and@name='selected[]']")).getAttribute("value"));
            contacts.add(new ContactData().withId(id).withFirstName(fname).withLastName(lname));
        }
        return contacts;
    }

    public Contacts all() {
        Contacts contacts = new Contacts();
        List<WebElement> elements = wd.findElements(By.cssSelector("[name='entry']"));
        for (WebElement element : elements) {
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            String fname = element.findElement(By.xpath("td[3]")).getText();
            String lname = element.findElement(By.xpath("td[2]")).getText();
            String allPhones = element.findElement(By.xpath("td[6]")).getText();
            String allEmails = element.findElement(By.xpath("td[5]")).getText();
            String address = element.findElement(By.xpath("td[4]")).getText();
            contacts.add(new ContactData().withId(id).withFirstName(fname).withLastName(lname).withAddress(address).withAllEmails(allEmails)
                    .withAllPhones(allPhones));
        }
        return contacts;
    }


}