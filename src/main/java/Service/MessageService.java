package Service;

import DAO.MessageDAO;
import Model.Message;
import java.util.List;

public class MessageService {

    private MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public Message newMessage (Message message){
        if(!(message.getMessage_text().isEmpty()) && (message.getMessage_text().length())<255) {
            return messageDAO.newMessage(message);
        }
        else{
            return null;
        }
           
    }

    public List<Message>  allMessages(){

        return messageDAO.getAllMessage();
    }

    public Message  getMessageById(int messageId){
        return messageDAO.getMessageById(messageId);
    }

    public Message deleteMessage(int messageId){
        return messageDAO.deleteMessageById(messageId);
    }


    public Message updatedMessage(int messageId, Message message){
        if (getMessageById(messageId)!=null && !message.getMessage_text().isEmpty() && message.getMessage_text().length()<255){
            return messageDAO.updateMessage(messageId, message);
        }else{
            return null;

        }

    }

    public List<Message>  allMessagesByUserId(int accountId){
        return messageDAO.getAllMessageByUserId(accountId);
    }

}
