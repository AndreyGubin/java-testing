package com.terrazor.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.testng.SkipException;

import java.io.IOException;
import java.util.Set;

public class TestBase {

    public Executor getExecutor() {
        return Executor.newInstance().auth("288f44776e7bec4bf44fdfeb1e646490", "");
    }

    public Set<Issue> getIssues() throws IOException {
        // если тест падает с ошибкой Sets differ, то нужно увеличить лимит получаемых записей
        // по умолчанию он равен 20 и если записей больше, то мы не сможем все их получить в запросе
        String json = getExecutor().execute(Request.Get("https://bugify.stqa.ru/api/issues.json?limit=100")).returnContent().asString();
        JsonElement parsed = JsonParser.parseString(json);
        JsonElement issues = parsed.getAsJsonObject().get("issues");
        return new Gson().fromJson(issues, new TypeToken<Set<Issue>>() {
        }.getType());
    }

    public int createIssue(Issue newIssue) throws IOException {
        String json = getExecutor().execute(Request.Post("https://bugify.stqa.ru/api/issues.json")
                .bodyForm(new BasicNameValuePair("subject", newIssue.getSubject()),
                        new BasicNameValuePair("description", newIssue.getDescription())))
                .returnContent().asString();
        JsonElement parsed = JsonParser.parseString(json);
        return parsed.getAsJsonObject().get("issue_id").getAsInt();
    }

    public void skipIfNotFixed(int issueId) throws IOException {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }

    private boolean isIssueOpen(int issueId) throws IOException {
        String status = getIssueStatus(issueId);
        if (status.equals("Resolved") || (status.equals("Closed"))) {
            return false;
        }
        return true;
    }

    private String getIssueStatus(int issueId) throws IOException {
        String json = getExecutor().execute(Request.Get(String.format("https://bugify.stqa.ru/api/issues/%s.json", issueId)))
                .returnContent().asString();

        JsonElement parsed = JsonParser.parseString(json);
        String status = parsed.getAsJsonObject().getAsJsonArray("issues").get(0).getAsJsonObject().get("state_name").getAsString();
        return status;
    }
}
