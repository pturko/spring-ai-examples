package com.pturko.ai.examples.hello;

import lombok.Data;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Scanner;


@Data
@SpringBootApplication
public class SpringAIHelloApplication implements CommandLineRunner {

	private final ChatClient chatClient;

	public SpringAIHelloApplication(ChatClient.Builder chatClientBuilder) {
		// Build the ChatClient instance using the provided builder
		this.chatClient = chatClientBuilder.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringAIHelloApplication.class, args);
	}

	@Override
	public void run(String... args) {
		printWelcomeMessage();

		try (Scanner scanner = new Scanner(System.in)) {
			while (true) {
				// Prompt the user for input
				System.out.print("> ");
				String input = scanner.nextLine().trim();

				// Process user input and fetch AI response
				processUserInput(input);
			}
		} catch (Exception e) {
			System.err.println("An error occurred while processing input: " + e.getMessage());
		}
	}

	private void processUserInput(String input) {
		try {
			// Fetch AI response using the chat client
			SpringAIResponse response = chatClient.prompt()
					.user(userSpec -> userSpec.text(input))
					.call()
					.entity(SpringAIResponse.class);

			// Print the response
			System.out.println(response.subject());
		} catch (Exception e) {
			System.err.println("Error while processing question: " + e.getMessage());
		}
	}

	public record SpringAIResponse(String subject, String request) {
	}

	private void printWelcomeMessage() {
		System.out.println("=======================================================");
		System.out.println("|| Hello World - Spring AI Command Line Application! ||");
		System.out.println("=======================================================");
		System.out.println();
	}

}
