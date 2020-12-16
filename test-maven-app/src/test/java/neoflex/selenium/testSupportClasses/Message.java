package neoflex.selenium;

class Message{
    
    String whom;
    String subject;
    String body;

    Message(String whom, String subject, String body){
        this.whom = whom;
        this.subject = subject;
        this.body = body;
    }

    String getWhom(){
        return whom;
    }

    String getSubject(){
        return subject;
    }

    String getBody(){
        return body;
    }
}
