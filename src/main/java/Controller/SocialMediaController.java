package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::newUserHandler);
        app.get("/register", this::getAllnewUser);
        app.post("/login", this::LoginHandler);
        app.post("/messages", this::newMessagesHandler);
        app.get("/messages", this::allMessagesHandler);
        app.get("/messages/{message_id}", this::messageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::allMessageByUserId);

        
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void newUserHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account User = accountService.newUser(account);
        if(User!=null){
            context.json(mapper.writeValueAsString(User));
            context.status(200);
        }else{
            context.status(400);
        }

    }

    public void getAllnewUser(Context context){
        List<Account> account = accountService.existingUser();
        context.json(account);
    }

    private void LoginHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account loginUser = accountService.loginUser(account);
        if(loginUser!=null){
            context.json(mapper.writeValueAsString(loginUser));
            context.status(200);
        }else{
            context.status(401);
        }

    }

    private void newMessagesHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message newMassage = messageService.newMessage(message);
        if(newMassage!=null){
            context.json(mapper.writeValueAsString(newMassage));
            context.status(200);
        }else{
            context.status(400);
        }

    }

    public void allMessagesHandler(Context context){
        List<Message> messages = messageService.allMessages();
        context.json(messages);
    }

    public void messageByIdHandler(Context context){
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        if(messageService.getMessageById(messageId)==null){
            context.status(200);
        }else{
            context.json(messageService.getMessageById(messageId));
        }

    }

    public void deleteMessageHandler(Context context){
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        if(messageService.deleteMessage(messageId)==null){
            context.status(200);
        }else{
            context.json(messageService.deleteMessage(messageId));
        }
    }

    private void updateMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message updatedMessage = messageService.updatedMessage(messageId, message);
        System.out.println(updatedMessage);
        if(updatedMessage == null){
            context.status(400);
        }else{
            context.json(mapper.writeValueAsString(updatedMessage));
        }

    }

    public void allMessageByUserId(Context context){
        int accountId = Integer.parseInt(context.pathParam("account_id"));
        List<Message> messages = messageService.allMessagesByUserId(accountId);
        context.json(messages);
    }

}