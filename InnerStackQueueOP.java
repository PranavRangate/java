import java.util.Scanner;

interface Stack {
    void push();
    void pop();
    void display_stack();
}

interface Queue {
    void EnQueue();
    void Dequeue();
    void display_Queue();
}

class StackQueueOP implements Stack, Queue {
    int MaxSize;
    int[] arr;
    int top = -1;
    int front = 0;
    int rear = -1;
    int x;
    Scanner sc = new Scanner(System.in);

    public StackQueueOP(int size) {
        MaxSize = size;
        arr = new int[MaxSize];
    }

    // Stack operations
    @Override
    public void push() {

        if (top >= MaxSize - 1) {
            System.out.println("Stack Overflow!");
            return;
        }

        System.out.println("Enter the element: ");
        x = sc.nextInt();
        top++;
        arr[top] = x;
        System.out.println("Element successfully added!");

    }

    @Override
    public void pop() {

        if (top < 0) {
            System.out.println("Stack Underflow!");
            return;
        }

        System.out.println("Element removed: " + arr[top]);
        top--;

    }

    @Override
    public void display_stack() {

        if (top < 0) {

            System.out.println("Stack is empty!");
            return;
        }

        System.out.println("Your Stack:");
        for (int i = 0; i <= top; i++) {
            System.out.println(arr[i]);
        }
        
    }

    // Queue operations
    @Override
    public void EnQueue() {

        if (rear >= MaxSize - 1) {

            System.out.println("Queue Overflow!");
            return;

        }

        System.out.println("Enter the element: ");
        x = sc.nextInt();
        rear++;
        arr[rear] = x;
        System.out.println("Element successfully added to the queue!");

    }

    @Override
    public void Dequeue() {

        if (front > rear) {

            System.out.println("Queue Underflow!");
            return;

        }

        System.out.println("Element removed from the queue: " + arr[front]);
        front++;

    }

    @Override
    public void display_Queue() {

        if (front > rear) {
            System.out.println("Queue is empty!");
            return;
        }

        System.out.println("Your Queue:");
        for (int i = front; i <= rear; i++) {
            System.out.println(arr[i]);
        }

    }
}

public class InnerStackQueueOP {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the max size of Stack and Queue:");
        int maxSize = sc.nextInt();
        
        StackQueueOP sq = new StackQueueOP(maxSize);

        int ch;
        int cs;
        int cq;
        do{

            System.out.println("1.Stack");
            System.out.println("2.Queue");
            System.out.println("3.Exit");
            System.out.println("Enter the choise no : ");
            ch=sc.nextInt();
            switch (ch) {
                case 1:
                    do{
                        System.out.println("1.push");
                        System.out.println("2.pop");
                        System.out.println("3.display");
                        System.out.println("4.Exit");
                        System.out.println("Enter the operation no : ");
                        cs=sc.nextInt();
                        switch (cs) {
                            case 1:
                                sq.push();
                                break;
                            case 2:
                                sq.pop();
                                break;
                            case 3:
                                sq.display_stack();
                                break;
                        
                            default:
                                break;
                        }

                    }while(cs!=4);
                    
                    break;


                    case 2:
                    do{
                        System.out.println("1.EnQueue");
                        System.out.println("2.Dequeue");
                        System.out.println("3.display");
                        System.out.println("4.Exit");
                        System.out.println("Enter the operation no : ");
                        cq=sc.nextInt();
                        switch (cq) {
                            case 1:
                                sq.push();
                                break;
                            case 2:
                                sq.pop();
                                break;
                            case 3:
                                sq.display_stack();
                                break;
                        
                            default:
                                break;
                        }

                    }while(cq!=4);

                default:
                    break;
            }

        }
        while(ch!=3);

/*         // Stack operations
        sq.push();
        sq.push();
        sq.pop();
        sq.display_stack();

        // Queue operations
        sq.EnQueue();
        sq.EnQueue();
        sq.Dequeue();
        sq.display_Queue();
*/
    }
}
