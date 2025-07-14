package com.example.controller;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
//public class SocialMediaController {

//}

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for the Social Media API.
 * Defines endpoints for user registration, login, and message operations.
 */
//
@RestController
public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    // Account Endpoints

    /**
     * Endpoint to register a new user account.
     * @param account JSON object containing username and password.
     * @return A response entity with the new account and status 200 on success, or status 409 on failure.
     */
    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account) {
        Account registeredAccount = accountService.registerAccount(account);
        if (registeredAccount != null) {
            return ResponseEntity.ok(registeredAccount);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    /**
     * Endpoint to log in a user.
     * @param account JSON object containing username and password.
     * @return A response entity with the account and status 200 on success, or status 401 on failure.
     */
    @PostMapping("/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account account) {
        Account loggedInAccount = accountService.loginAccount(account);
        if (loggedInAccount != null) {
            return ResponseEntity.ok(loggedInAccount);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // Message Endpoints

    /**
     * Endpoint to create a new message.
     * @param message JSON object containing message details.
     * @return A ResponseEntity containing the created message and status 200, or status 400 on failure.
     */
    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message createdMessage = messageService.createMessage(message);
        if (createdMessage != null) {
            return ResponseEntity.ok(createdMessage);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Endpoint to retrieve all messages.
     * @return A list of all messages.
     */
    @GetMapping("/messages")
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    /**
     * Endpoint to retrieve a message by its ID.
     * @param message_id The ID of the message.
     * @return The message with the specified ID.
     */
    @GetMapping("/messages/{message_id}")
    public Message getMessageById(@PathVariable("message_id") int message_id) {
        return messageService.getMessageById(message_id);
    }

    /**
     * Endpoint to delete a message by its ID.
     * @param message_id The ID of the message to delete.
     * @return A response entity with status 200. The body will contain the integer 1 if a message was deleted,
     * or an empty body if no message was found.
     */
    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Object> deleteMessageById(@PathVariable("message_id") int message_id) {
        Message deletedMessage = messageService.deleteMessageById(message_id);
        if (deletedMessage != null) {
            return ResponseEntity.ok(1);
        } else {
            return ResponseEntity.ok().body("");
        }
    }

    /**
     * Endpoint to update a message's text.
     * @param message_id The ID of the message to update.
     * @param message JSON object containing the new message text.
     * @return A response entity with status 200 and body 1 on success, or status 400 on failure.
     */
    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<Object> updateMessageById(@PathVariable("message_id") int message_id, @RequestBody Message message) {
        Message updatedMessage = messageService.updateMessageById(message_id, message);
        if (updatedMessage != null) {
            return ResponseEntity.ok(1);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Endpoint to retrieve all messages from a specific user.
     * @param account_id The ID of the user.
     * @return A list of messages from the specified user.
     */
    @GetMapping("/accounts/{account_id}/messages")
    public List<Message> getAllMessagesFromUser(@PathVariable("account_id") int account_id) {
        return messageService.getAllMessagesFromUser(account_id);
    }
}
