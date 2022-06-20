import java.util.ArrayList;


public class MessageQueue<T> {
 
    ArrayList<T> queue = new ArrayList<T>();
 
    public synchronized void enqueue(T msg) {
        System.out.println("Enqueued the message");
        this.queue.add(msg);
        notify();
    }
 
    public synchronized T dequeue() {
        System.out.println("Inside Dequeue");
        while (this.queue.isEmpty()) {
            try {
                System.out.println("Inside Dequeue -- Waiting");
                wait();
            } catch (Exception ex) {
                System.out.println("Exception occured in Dequeue");
            }
        }
        System.out.println("Dequeue -- Completed");
        return this.queue.remove(0);
    }
}