package com.tinderAI;

import com.tinderAI.conversations.ChatMessage;
import com.tinderAI.conversations.Conversation;
import com.tinderAI.conversations.ConversationRepository;
import com.tinderAI.profile.Gender;
import com.tinderAI.profile.Profile;
import com.tinderAI.profile.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class TinderAiBackendApplication implements CommandLineRunner {

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private ConversationRepository conversationRepository;

	public static void main(String[] args) {
		SpringApplication.run(TinderAiBackendApplication.class, args);
	}

	public void run(String... args){
//		System.out.println("My app is running!");
		profileRepository.deleteAll();
		conversationRepository.deleteAll();
		Profile profile = new Profile(
				"1", // id
				"Jainam", // firstName
				"Thakkar", // lastName
				"Software developer", // bio
				21, // age
				"", // imageUrl
				"Indian", // ethnicity
				Gender.MALE, // gender
				"" // personalityType
		);
		profileRepository.save(profile);

		Profile profile2 = new Profile(
				"2", // id
				"foo", // firstName
				"bar", // lastName
				"Software developer", // bio
				24, // age
				"foo.png", // imageUrl
				"Indian", // ethnicity
				Gender.FEMALE, // gender
				"ABCD" // personalityType
		);
		profileRepository.save(profile2);
		profileRepository.findAll().forEach(System.out::println);
//		System.out.println(profile);

		Conversation conversation = new Conversation(
				"1",
				profile.id(),
				List.of(
					new ChatMessage("Hello", profile.id(), LocalDateTime.now())
				)
		);
		conversationRepository.save(conversation);
		conversationRepository.findAll().forEach(System.out::println);
	}
}
