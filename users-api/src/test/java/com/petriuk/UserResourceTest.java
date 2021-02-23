package com.petriuk;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
public class UserResourceTest {
    private static final String SERVICE_URL = "http://localhost:8080/rest/user";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";
    private static final String CONTENT_JSON = "application/json";
    private static String authHeader;

    @BeforeAll
    public static void beforeClass() {
        String encoding = Base64.getEncoder()
            .encodeToString((USERNAME + ":" + PASSWORD).getBytes());
        authHeader = "Basic " + encoding;
    }

    public void initHeaders(HttpUriRequest request) {
        request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
        request.setHeader(HttpHeaders.CONTENT_TYPE, CONTENT_JSON);
    }

    private int getStatusCode(HttpUriRequest request)
        throws IOException {
        HttpResponse httpResponse = HttpClientBuilder.create().build()
            .execute(request);
        return httpResponse.getStatusLine().getStatusCode();
    }

    private String getResponseBody(HttpUriRequest request)
        throws IOException {
        HttpResponse httpResponse = HttpClientBuilder.create().build()
            .execute(request);
        return EntityUtils.toString(httpResponse.getEntity());
    }

    private String getJsonFileContent(String path)
        throws IOException, URISyntaxException {
        return new String(
            Files.readAllBytes(Paths.get(getClass().getResource(path).toURI())));
    }

    @Test
    public void getAllUsersGetShouldReturn200OK() throws IOException {
        HttpUriRequest request = new HttpGet(SERVICE_URL);
        initHeaders(request);
        assertEquals(getStatusCode(request), HttpStatus.SC_OK);
    }

    @Test
    public void getAllUsersShouldReturn405IfNotGetOrPostRequest()
        throws IOException {
        HttpUriRequest request = new HttpDelete(SERVICE_URL);
        initHeaders(request);

        assertEquals(getStatusCode(request), HttpStatus.SC_METHOD_NOT_ALLOWED);
    }

    @Test
    public void serviceShouldReturn401IfNotAuthorized() throws IOException {
        HttpUriRequest requestNotAuthrorized = new HttpGet(SERVICE_URL);

        assertEquals(getStatusCode(requestNotAuthrorized), HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    public void serviceShouldReturn401IfWrongCredentials() throws IOException {
        HttpUriRequest request = new HttpGet(SERVICE_URL);
        String authWrong = "Basic " + Base64.getEncoder()
            .encodeToString(("wrong" + ":" + "credentials").getBytes());
        request.setHeader(HttpHeaders.AUTHORIZATION, authWrong);

        assertEquals(getStatusCode(request), HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    public void serviceShouldReturn403IfWrongRole() throws IOException {
        HttpUriRequest request = new HttpGet(SERVICE_URL);
        String authWrong = "Basic " + Base64.getEncoder()
            .encodeToString(("user" + ":" + "user").getBytes());
        request.setHeader(HttpHeaders.AUTHORIZATION, authWrong);

        assertEquals(getStatusCode(request), HttpStatus.SC_FORBIDDEN);
    }

    @Test
    public void getByUsernameShouldReturnValidJSON()
        throws IOException, URISyntaxException {
        HttpUriRequest request = new HttpGet(SERVICE_URL + "/admin");
        initHeaders(request);

        String expected = getJsonFileContent("/json/admin.json");
        String actual = getResponseBody(request);

        assertEquals(expected, actual);
    }

    @Test
    void getUserShouldReturn404IfNotFound() throws IOException {
        HttpUriRequest request = new HttpGet(SERVICE_URL + "/adminInvalid");
        initHeaders(request);

        assertEquals(HttpStatus.SC_NOT_FOUND, getStatusCode(request));
    }

    @Test
    void addUserShouldReturn200IfValidUser()
        throws IOException, URISyntaxException {
        HttpPost request = new HttpPost(SERVICE_URL);
        initHeaders(request);
        request.setEntity(new StringEntity(getJsonFileContent("/json/new_user.json")));

        assertEquals(HttpStatus.SC_CREATED, getStatusCode(request));
        deleteUser("newuser");
    }

    @Test
    void addUserShouldReturn409IfUserExists()
        throws IOException, URISyntaxException {
        HttpPost request = new HttpPost(SERVICE_URL);
        initHeaders(request);
        request.setEntity(new StringEntity(getJsonFileContent("/json/new_user.json")));

        assertEquals(HttpStatus.SC_CREATED, getStatusCode(request));
        assertEquals(HttpStatus.SC_CONFLICT, getStatusCode(request));

        deleteUser("newuser");
    }

    @Test
    void addUserShouldReturn422AndErrorsJsonIfNotValidObject()
        throws IOException, URISyntaxException {
        HttpPost request = new HttpPost(SERVICE_URL);
        initHeaders(request);
        request.setEntity(new StringEntity(getJsonFileContent("/json/invalid_entity.json")));
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        String expectedJson = getJsonFileContent("/json/invalid_entity_response.json");
        String actualJson = EntityUtils.toString(response.getEntity());

        assertEquals(HttpStatus.SC_UNPROCESSABLE_ENTITY, response.getStatusLine().getStatusCode());
        assertEquals(expectedJson, actualJson);
    }

    @Test
    void putUserShouldReturn404IfNotFound()
        throws IOException, URISyntaxException {
        HttpPut request = new HttpPut(SERVICE_URL);
        initHeaders(request);
        request.setEntity(new StringEntity(getJsonFileContent("/json/admin_invalid.json")));

        assertEquals(HttpStatus.SC_NOT_FOUND, getStatusCode(request));
    }

    @Test
    void putUserShouldReturn204IfValidUser()
        throws IOException, URISyntaxException {
        HttpPost postRequest = new HttpPost(SERVICE_URL);
        initHeaders(postRequest);
        postRequest.setEntity(new StringEntity(getJsonFileContent("/json/new_user.json")));
        assertEquals(HttpStatus.SC_CREATED, getStatusCode(postRequest));

        HttpPut putRequest = new HttpPut(SERVICE_URL);
        initHeaders(putRequest);
        putRequest.setEntity(new StringEntity(getJsonFileContent("/json/new_user.json")));

        assertEquals(HttpStatus.SC_NO_CONTENT, getStatusCode(putRequest));
        deleteUser("newuser");
    }

    @Test
    void putUserShouldReturn422AndErrorsJsonIfNotValidObject()
        throws IOException, URISyntaxException {
        HttpPut request = new HttpPut(SERVICE_URL);
        initHeaders(request);
        request.setEntity(new StringEntity(getJsonFileContent("/json/invalid_entity.json")));
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        String expectedJson = getJsonFileContent("/json/invalid_entity_response.json");
        String actualJson = EntityUtils.toString(response.getEntity());

        assertEquals(HttpStatus.SC_UNPROCESSABLE_ENTITY, response.getStatusLine().getStatusCode());
        assertEquals(expectedJson, actualJson);
    }

    @Test
    void deleteUserShouldReturn404IfNotFound() throws IOException {
        HttpUriRequest request = new HttpDelete(SERVICE_URL + "/adminInvalid");
        initHeaders(request);

        assertEquals(HttpStatus.SC_NOT_FOUND, getStatusCode(request));
    }

    @Test
    void deleteUserShouldReturn204IfSuccessful()
        throws IOException, URISyntaxException {
        HttpPost postRequest = new HttpPost(SERVICE_URL);
        initHeaders(postRequest);
        postRequest.setEntity(new StringEntity(getJsonFileContent("/json/new_user.json")));
        assertEquals(HttpStatus.SC_CREATED, getStatusCode(postRequest));

        HttpDelete deleteRequest = new HttpDelete(SERVICE_URL + "/newuser");
        initHeaders(deleteRequest);
        assertEquals(HttpStatus.SC_NO_CONTENT, getStatusCode(deleteRequest));
    }

    private void deleteUser(String login) throws IOException {
        HttpUriRequest request = new HttpDelete(SERVICE_URL + "/" + login);
        initHeaders(request);
        HttpClientBuilder.create().build().execute(request);
    }
}
