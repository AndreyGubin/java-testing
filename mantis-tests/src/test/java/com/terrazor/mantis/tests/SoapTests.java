package com.terrazor.mantis.tests;

import com.terrazor.mantis.model.Issue;
import com.terrazor.mantis.model.Project;
import org.testng.annotations.Test;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class SoapTests extends TestBase {

    @Test
    public void testGetProjects() throws MalformedURLException, ServiceException, RemoteException {

        Set<Project> projects = app.soap().getProjects();
        System.out.println(projects.size());
        for (Project project : projects) {
            System.out.println(project.getName());
        }
    }

    @Test
    public void testCreateIssue() throws MalformedURLException, ServiceException, RemoteException {
        Set<Project> projects = app.soap().getProjects();
        Issue issue = new Issue().withSummary("Test issue")
                .withDescription("Test issue description").withProject(projects.iterator().next());
        Issue created = app.soap().addIssue(issue);
        assertEquals(issue.getSummary(), created.getSummary());
    }

    @Test
    public void testStatusIssue() throws RemoteException, ServiceException, MalformedURLException {
        // указываем номер задачи в багтрекере
        skipIfNotFixed(3);
        // если у задачи статус "решена" или "закрыта", то продолжаем выполнение теста и выводим сообщение
        // если статус другой, то пропускаем тест и ставим метку ignored
        System.out.println("Test Started! ");
    }
}
