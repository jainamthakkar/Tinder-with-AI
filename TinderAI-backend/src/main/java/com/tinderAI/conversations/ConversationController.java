package com.tinderAI.conversations;

import com.tinderAI.profile.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ConversationController {

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @PostMapping("/conversations")
    public Conversation NewConversation(@RequestBody CreateConversationRequest request) {

        conversationRepository.findById(request.profileId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find conversation " + request.profileId()));
        Conversation conversation = new Conversation(
                UUID.randomUUID().toString(),
                request.profileId(),
                new ArrayList<>()
        );
        conversationRepository.save(conversation);
        return conversation;
    }

    @GetMapping("/conversations/{conversationId}")
    public Conversation getConversation(@PathVariable String conversationId) {
        return conversationRepository.findById(conversationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find conversation " + conversationId));
    }

        @PostMapping("/conversations/{conversationId}")
    public Conversation addMessage(@PathVariable String conversationId, @RequestBody ChatMessage chatMessage) {

        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find conversation " + conversationId));

        profileRepository.findById(chatMessage.authorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find Author " + chatMessage.authorId()));

        ChatMessage newMessage = new ChatMessage(chatMessage.messageText(), chatMessage.authorId(), LocalDateTime.now());

        conversation.messages().add(newMessage);
        conversationRepository.save(conversation);
        return conversation;
    }

    public record CreateConversationRequest(
            String profileId
    ) {
    }
}
