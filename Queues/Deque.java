import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node front;
    private Node rear;
    private int size;
    private class Node{
        Item item;
        Node prev;
        Node next;
        Node(Item item){
            this.item = item;
        }

    }    
    // construct an empty deque
    public Deque(){
        front = new Node(null);
        rear = new Node(null);
        front.next = rear;
        rear.prev = front;
    }

    // is the deque empty?
    public boolean isEmpty(){
        return size == 0;
    }

    // return the number of items on the deque
    public int size(){
        return size;
    }

    // add the item to the front
    public void addFirst(Item item){
        if(item == null){
            throw new NullPointerException("Element cannot be null");
        }
        Node node = new Node(item);
        node.next = front.next;
        node.prev = front;
        front.next.prev = node;
        front.next = node;
        size++;
    }

    // add the item to the back
    public void addLast(Item item){
        if(item == null){
            throw new NullPointerException("Element cannot be null");
        }
        Node node = new Node(item);
        node.next = rear;
        node.prev = rear.prev;
        rear.prev.next = node;
        rear.prev = node;
        size++; 
    }

    // remove and return the item from the front
    public Item removeFirst(){
        if(size==0){
            throw new NoSuchElementException("Deque is empty");
        }
        Node node = front.next;
        front.next = node.next;
        front.next.prev = front;
        size--;
        return node.item;
        
    }

    // remove and return the item from the back
    public Item removeLast(){
        if(size==0){
            throw new NoSuchElementException("Deque is empty");
        }
        Node node = rear.prev;
        rear.prev = node.prev;
        rear.prev.next = rear;
        size--;
        return node.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator(){
        return new FrontIterator();
    }
    public class FrontIterator implements Iterator<Item>{
        private Node curr = front;
        // @Override
        public boolean hasNext(){
            return curr.next != rear;
        }
        // @Override
        public Item next(){
            if(!hasNext()){
                throw new NoSuchElementException("The Deque has ended");
            }
            curr = curr.next;
            return curr.item;
        }
        // @Override
        public void remove(){
            throw new UnsupportedOperationException("Remove not supported");
        }
    }

    // unit testing (required)
    public static void main(String[] args){
        StdOut.println("Test Starts");
        //Test 1
        Deque<Integer> d1 = new Deque<>();
        StdOut.println("Test 1A  "+d1.isEmpty());
        d1.addFirst(0);
        StdOut.println("Test 1B  "+(d1.size()==1));
        d1.addLast(1);
        d1.addLast(2);
        StdOut.println("Test 1C  "+(d1.removeFirst()==0));
        StdOut.println("Test 1D  "+(d1.iterator().next() == 1));
        StdOut.println("Test 1E  "+(d1.removeLast()==2));
        StdOut.println("Test 1F  "+(d1.removeFirst()==1));
        StdOut.println("Test 1F  "+(d1.size()==0));
        StdOut.println("Test 1G  "+(!d1.iterator().hasNext()));
        //Test 2
        Deque<Integer> d2 = new Deque<Integer>();
        try {
            d2.removeFirst();
            StdOut.println("Test 2A : ");
        } catch (Exception e) {
            boolean r = e instanceof NoSuchElementException;
            StdOut.println("Test 2A : "+r);            
        }
        try {
            d2.removeLast();
            StdOut.println("Test 2B : ");
        } catch (Exception e) {
            boolean r = e instanceof NoSuchElementException;
            StdOut.println("Test 2B : "+r);
        }
        try {
            d2.addFirst(null);
            StdOut.println("Test 2C : ");
        } catch (Exception e) {
            boolean r = e instanceof NullPointerException;
            StdOut.println("Test 2C : "+r);    
        }
        try {
            d2.addLast(null);
            StdOut.println("Test 2D : ");
        } catch (Exception e) {
            boolean r = e instanceof NullPointerException;
            StdOut.println("Test 2D : "+r);
        }
        try {
            d2.iterator().remove();
            StdOut.println("Test 2E : ");
        } catch (Exception e) {
            boolean r = e instanceof UnsupportedOperationException;
            StdOut.println("Test 2E : "+r);
        }
        try {
            d2.iterator().next();
            StdOut.println("Test 2F : ");
        } catch (Exception e) {
            boolean r = e instanceof NoSuchElementException;
            StdOut.println("Test 2F : "+r);
        }
        // Test 3
        Deque<String> d3 = new Deque<String>();
        d3.addFirst("Hello World");
        StdOut.println("Test 3 : "+(d3.removeFirst()=="Hello World"));
        Deque<Double> d4 = new Deque<Double>();
        d4.addLast(123.45);
        StdOut.println("Test 4 : "+true);
}
}