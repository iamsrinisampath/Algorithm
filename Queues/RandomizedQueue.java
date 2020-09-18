import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] array;
    private int lastIndex;

    // construct an empty randomized queue
    public RandomizedQueue(){
        Item[] a = (Item[]) new Object(1);
        lastIndex = -1;
        array = a; 
    }

    // is the randomized queue empty?
    public boolean isEmpty(){
        return size() == 0;
    }

    // return the number of items on the randomized queue
    public int size(){
        return lastIndex+1;
    }

    // add the item
    public void enqueue(Item item){
        if(item == null){
            throw new NullPointerException("Cannot insert null");
        }
        if(lastIndex+1==array.length){
            resize(array.length*2);
        }
        array[++lastIndex] = item;
    }

    // remove and return a random item
    public Item dequeue(){
        if(size == 0){
            throw new NoSuchElementException("Size of the array is zero");
        }
        int x = StdRandom.uniform(lastIndex+1);
        Item y = array[i];
        array[i] = array[lastIndex];
        array[lastIndex--] = null;
        if(size()>0&&size()==capacity/4){
            resize(capacity/2);
        }
        return y;
    }
    private void resize(int capacity){
        Item[] newArray = (Item[]) new Object(capacity);
        int i=0, j=0;
        while(i<=lastIndex){
            newArray[j++] = array[i++];
        }
        array = newArray;
        lastIndex=j-1;
    }
    // return a random item (but do not remove it)
    public Item sample(){
        return array[StdOut.uniform(lastIndex+1)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        return new RandomizedIterator();
    }
    public class RandomizedIterator implements Iterator<Item>{

    }

    // unit testing (required)
    public static void main(String[] args){
        
    }

}