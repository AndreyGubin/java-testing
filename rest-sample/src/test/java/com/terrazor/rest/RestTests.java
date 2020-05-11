package com.terrazor.rest;

import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class RestTests extends TestBase {

    @Test
    public void testCreateIssue() throws IOException {
        Set<Issue> oldIssues = getIssues();
        Issue newIssue = new Issue().withSubject("Test issue").withDescription("New test issue");
        int issueId = createIssue(newIssue);
        Set<Issue> newIssues = getIssues();
        oldIssues.add(newIssue.withId(issueId));
        // если тест падает с ошибкой Sets differ, то нужно увеличить лимит получаемых записей в файле TestBase
        // по умолчанию он равен 20 и если записей больше, то мы не сможем все их получить в запросе
        assertEquals(newIssues, oldIssues);
    }

    @Test
    public void testStatusIssue() throws IOException {
        // указываем номер задачи в багтрекере
        skipIfNotFixed(21);
        // если у задачи статус "решена" или "закрыта", то продолжаем выполнение теста и выводим сообщение
        // если статус другой, то пропускаем тест и ставим метку ignored
        System.out.println("Test Started! ");
    }
}