package org.networkedassets.atlassian.stash.privaterepos.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.eclipse.jetty.util.UrlEncoded;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestDataGenerator {

	private final Logger log = LoggerFactory.getLogger(TestDataGenerator.class);

	private static final String REST_API_URL = "http://localhost:7990/stash/rest/api/1.0/";
	private static final int NUMBER_OF_USERS = 100;
	private static final double HAVING_PERSONAL_REPOSITORY_PROBABILITY = 0.9;
	private static final int MAX_REPOS_PER_USER = 10;
	private HttpClient client;
	private Set<String> userNames;

	public TestDataGenerator() {
		createHttpClient();
	}

	public static void main(String[] args) {
		TestDataGenerator testDataGenerator = new TestDataGenerator();
		testDataGenerator.generate();
	}

	private void createHttpClient() {
		RequestConfig config = RequestConfig.custom().setSocketTimeout(1000)
				.setConnectTimeout(1000).setConnectionRequestTimeout(1000)
				.build();
		client = HttpClients.custom().setDefaultRequestConfig(config).build();
	}

	public void generate() {
		createHttpClient();
		createUsers();
		createRepositories();
	}

	private void createUsers() {
//		userNames = generateRandomNames(NUMBER_OF_USERS);
		userNames = readFakeNamesFromFile();
		System.out.println("Random names generated {}" + userNames);
		for (String name : userNames) {
			createUser(name);
		}
	}

	private Set<String> generateRandomNames(int number) {
		Set<String> names = new HashSet<String>();
		for (int i = 0; i < number; i++) {
			names.add(createRandomName());
		}
		return names;
	}

	private Set<String> readFakeNamesFromFile() {
		Set<String> names = new LinkedHashSet<String>();
		try {
			System.out.println(new File(".").getAbsolutePath());
			Scanner scanner = new Scanner(new File("src/test/resources/FakeNames.csv"));
			scanner.useDelimiter(",");
			while(scanner.hasNextLine()){
				names.add(scanner.nextLine());
	        }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return names;
	}

	private void createUser(String name) {
		String slug = slugifyName(name);
		HttpPost post = new HttpPost(REST_API_URL + "admin/users?name=" + slug
				+ "&password=" + slug + "&displayName=" + UrlEncoded.encodeString(name)
				+ "&emailAddress=" + slug + "@networkedassets.org");
		makePostAsAdmin(post);
	}
	
	private String slugifyName(String name) {
		name = name.replace(' ', '_');
		name = name.toLowerCase();
		return name;
	}

	private void createRepositories() {
		for (String name : userNames) {
			createUserRepositories(name);
		}
	}

	private void createUserRepositories(String userName) {
		Random rnd = new Random();
		boolean hasPersonalRepos = rnd.nextDouble() < HAVING_PERSONAL_REPOSITORY_PROBABILITY;
		if (!hasPersonalRepos) {
			return;
		}
		System.out.println(userName + "Has personal repos !");
		int numberOfRepos = rnd.nextInt(MAX_REPOS_PER_USER) + 1;
//		Set<String> randomReposNames = generateRandomNames(numberOfRepos);
		for (int i = 0; i < numberOfRepos; i++) {
			System.out.println("Creating user " + userName + " repo Repository_" + i);
			createUserRepository(userName, "Repository_" + i);
		}
	}

	private void createUserRepository(String userName, String repoName) {
		// only forking works with rest API
		HttpPost post = new HttpPost(REST_API_URL
				+ "projects/PROJECT_1/repos/rep_1");
		StringEntity requestEntity = null;
		try {
			requestEntity = new StringEntity("{\"name\": \"" + repoName
					+ "\"}");
			requestEntity.setContentType("application/json");
			post.setEntity(requestEntity);
			String userSlug = slugifyName(userName);
			makePost(post, userSlug + ":" + userSlug);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
	}

	private String createRandomName() {
		return RandomStringUtils.randomAlphanumeric(15);
	}

	private HttpResponse makePost(HttpPost post, String auth) {
		try {
			String encodedAuth = new String(
					Base64.encodeBase64(auth.getBytes()));
			post.setHeader("Authorization", "Basic " + encodedAuth);

			HttpResponse response = client.execute(post);
			System.out.println("Request" + post);
			System.out.println("Response" + response);
			post.releaseConnection();
			return response;
		} catch (ClientProtocolException e) {
			System.out.println("Client protocol exception");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO exception");
			e.printStackTrace();
		}
		return null;
	}

	private HttpResponse makePostAsAdmin(HttpPost post) {
		return makePost(post, "admin:admin");
	}

}
