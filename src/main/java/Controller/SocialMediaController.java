package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

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
    
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", null);
        app.post("login", null);
        app.post("/messages", null);
        app.get("/messages", null);
        app.get("/messages/{message_id}", null);
        app.delete("/messages/{message_id}", null);
        app.patch("/messages/{message_id}", null);
        app.get("/accounts/{account_id}/messages", null);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    /* Handler for creating new account (registration)
    */
    private void postRegistrationHandler(Context context) {
        context.json("sample text");
    }

    /* Handler for logging in to account
     */
    private void postLoginHandler(Context context) {

    }

    /* Handler for Creating Message
     */
    private void postCreateMessage(Context context){

    }

    /* Handler for Updating Message
     */
    private void patchUpdateMessage(Context context){

    }

    /* Handler for finding Message by ID
     */
    private void getMessageByID(Context context) {

    }

    /* Handler for getting all messages
     */
    private void getAllMessages(Context context) {

    }

     /* Handler for getting all messages from a user
      */
    private void getMessagedByUser(Context context) {

    }

    /* Handler for deleting message by ID
     */
    private void deleteMessageByID(Context context) {

    }

}