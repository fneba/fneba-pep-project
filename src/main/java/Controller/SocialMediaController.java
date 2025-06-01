package Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }   
    
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postRegistrationHandler);
        app.post("login", this::postLoginHandler);
        app.post("/messages", this::postCreateMessage);
        app.get("/messages", this::getAllMessages);
        app.get("/messages/{message_id}", this::getMessageByID);
        app.delete("/messages/{message_id}", this::deleteMessageByID);
        app.patch("/messages/{message_id}", this::patchUpdateMessage);
        app.get("/accounts/{account_id}/messages", this::getMessagesByUser);

        return app;
    }

    // Handler for creating new account (registration)
    private void postRegistrationHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);

        if (account.getUsername() == null || account.getUsername().isBlank() 
        || account.getPassword() == null || account.getPassword().length() < 4) {
            ctx.status(400);
            return;
        }

        Account existingAccount = accountService.getAccountByUsername(account.getUsername());

        if (existingAccount != null){
            ctx.status(400);
            return;
        }

        System.out.println("Calling registerNewAccount for: " + account.getUsername());

        Account createdAccount = accountService.registerNewAccount(account);
        ctx.json(createdAccount);
    }

    // Handler for logging in to account
    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account loginAccount = mapper.readValue(ctx.body(), Account.class);
        Account account = accountService.loginToAccount(loginAccount.getUsername(), loginAccount.getPassword());

        if (account == null){
            ctx.status(401);
            return;
        } else {
            ctx.json(account);
        }
    }

    // Handler for Creating Message
    private void postCreateMessage(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        
        String newMessageText = message.getMessage_text();

        if(newMessageText==null || newMessageText.isBlank() || newMessageText.length() > 255){
            ctx.status(400);
            return;
        }
        
        Message newMessage = messageService.createNewMessage(message);
        if (newMessage == null) {
            ctx.status(400);
            return;
        }
        ctx.json(newMessage);
    }

    // Handler for getting all messages
    private void getAllMessages(Context ctx) throws JsonProcessingException {
        List<Message> messages = messageService.getMessages();
        ctx.json(messages);
    }

    // Handler for finding Message by ID
    private void getMessageByID(Context ctx) throws JsonProcessingException {
        int messageID = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageByMessageId(messageID);

        if(message == null){
            ctx.status(200).result("");
        } else {
            ctx.json(message);
        }
    }

    // Handler for deleting message by ID
    private void deleteMessageByID(Context ctx) throws JsonProcessingException {
        int messageID = Integer.parseInt(ctx.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessage(messageID);

        if (deletedMessage == null){
            ctx.status(200).result("");
        } else {
            ctx.json(deletedMessage);
        }
    }

    // Handler for Updating Message
    private void patchUpdateMessage(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));

        String newText = message.getMessage_text();
        if (newText == null || newText.isBlank() || newText.length() > 255){
            ctx.status(400);
            return;
        }

        messageService.updateForMessage(message_id, newText);
        Message updated = messageService.getMessageByMessageId(message_id);

        if(updated == null || !updated.getMessage_text().equals(newText)){
            ctx.status(400);
            return;
        }else{
            ctx.json(updated);
        }
    }
    
    // Handler for getting all messages from a user
    private void getMessagesByUser(Context ctx) throws JsonProcessingException {
        int accountID = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getUsersMessages(accountID);

        ctx.json(messages);
    }

    

}