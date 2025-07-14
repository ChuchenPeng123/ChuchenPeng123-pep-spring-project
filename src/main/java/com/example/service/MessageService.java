//package com.example.service;

//public class MessageService {
//}
package com.example.service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class to handle business logic for Messages.
 */
@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    /**
     * Creates a new message after validation.
     *
     * @param message The message to be created.
     * @return The created message if successful, otherwise null.
     */
    public Message createMessage(Message message) {
        // Validation: message text must not be blank and must be under 255 characters
        if (message.getMessageText() == null || message.getMessageText().isBlank() || message.getMessageText().length() >= 255) {
            return null;
        }
        // Validation: user must exist
        if (message.getPostedBy() == null || !accountRepository.existsById(message.getPostedBy())) {
            return null;
        }
        return messageRepository.save(message);
    }

    /**
     * Retrieves all messages.
     *
     * @return A list of all messages.
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    /**
     * Retrieves a single message by its ID.
     *
     * @param messageId The ID of the message to retrieve.
     * @return The message if found, otherwise null.
     */
    public Message getMessageById(int messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }

    /**
     * Deletes a message by its ID.
     *
     * @param messageId The ID of the message to delete.
     * @return The deleted message if found and deleted, otherwise null.
     */
    public Message deleteMessageById(int messageId) {
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        if (messageOptional.isPresent()) {
            messageRepository.deleteById(messageId);
            return messageOptional.get();
        }
        return null;
    }

    /**
     * Updates a message's text by its ID.
     *
     * @param messageId The ID of the message to update.
     * @param message   The message object containing the new text.
     * @return The updated message if successful, otherwise null.
     */
    public Message updateMessageById(int messageId, Message message) {
        // New message text must be valid
        if (message.getMessageText() == null || message.getMessageText().isBlank() || message.getMessageText().length() >= 255) {
            return null;
        }
        
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        if (messageOptional.isPresent()) {
            Message existingMessage = messageOptional.get();
            existingMessage.setMessageText(message.getMessageText());
            return messageRepository.save(existingMessage);
        }
        return null;
    }

    /**
     * Retrieves all messages posted by a specific user.
     *
     * @param accountId The ID of the user.
     * @return A list of messages from the specified user.
     */
    public List<Message> getAllMessagesFromUser(int accountId) {
        return messageRepository.findAllMessagesByPostedBy(accountId);
    }
}
