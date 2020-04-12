import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.naming.*;
import javax.jms.*;

public class MySender {
    public static void main(String[] args) {

        try{ 
            //1. Create and start connection
            InitialContext ctx = new InitialContext();
            TopicConnectionFactory f = (TopicConnectionFactory)ctx.lookup("myTopic ConnectionFactory");
            TopicConnection con = f.createTopicConnection();
            con.start();

            //2. Create queue session
            TopicSession ses = con.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

            //3. Get the Topic object
            Topic t = (Topic)ctx.lookup("myTopic");

            //4. Create TopicPublisher object
            TopicPublisher publisher=ses.createPublisher(t);

            //5. Create TextMessage object
            TextMessage msg = ses.createTextMessage();

            //6. Write message
            BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
            
            while(true){
                System.out.println("Enter Message, end to terminate:");
                String s = b.readLine();
                if (s.equals("end"))
                break;
                msg.setText(s);

                //7. Send message
                publisher.publish(msg);
                System.out.println("Message successfully sent.");
            }

            //8. Connection close
            con.close();

        }catch(Exception e){
            System.out.println(e);
        }
    }
}
