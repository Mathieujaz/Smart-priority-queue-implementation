
//This class is to create a key-value pair
class Entry{
    int key;
    Object value;   //Object is used so that we can put any type of data

    //Constructor for the entry class
    public Entry(int key, Object value){
        this.key = key;
        this.value = value;
    }
    
    public String toString(){
        return ("(" + key + ", " + value + ")");      //to string method to show clearly the values and keys 
    }

}

//SPQ class 
public class SPQ {

    private Entry[] heap;
    private int size;
    private String state;

    //constructor for a smarter priority queue
    public SPQ( int initSize){
        size = 0;
        state = "Min";
        heap = new Entry[initSize];
    }
    //Toggle changes the state of the heap and changes it if its a min-heap or max-heap
    public void toggle(){
        state = state.equals("Min") ? "Max" : "Min";
        rebuildHeap();
    }
    //returns the top element of the heap
    public Entry removeTop(){
        if (isEmpty()){
            return null;
        }
        Entry top = heap[0];
        heap[0] = heap[size -1];
        size--;
        heapifyDown(0);
        return top;
    }
    //inserts a new entry to the heap
    public Entry insert(int key, Object value){
        if (size == heap.length){
            extendHeap();
        }
        Entry newEntry = new Entry(key, value);
        heap[size] = newEntry;
        heapifyUp(size);
        size++;
        return newEntry;
    }
    //returns the top element
    public Entry top(){
        return isEmpty() ? null: heap[0];
    }
    //Removes the chosen entry
    public Entry remove(Entry e) {
        int index = findIndex(e);
        if (index == -1) return null;

        Entry removed = heap[index];
        heap[index] = heap[size - 1];
        size--;
        if (index < size) {
            heapifyDown(index);
            heapifyUp(index);
        }
        return removed;
    }
    //replaces the key of an entry
    public int replaceKey(Entry e, int newKey) {
        int index = findIndex(e);
        if (index == -1) return -1;
        int oldKey = heap[index].key;
        heap[index].key = newKey;
        heapifyDown(index);
        heapifyUp(index);
        return oldKey;
    }
    //replaces the value of an entry
    public Object replaceValue(Entry e, Object newValue) {
        int index = findIndex(e);
        if (index == -1) return null;
        Object oldValue = heap[index].value;
        heap[index].value = newValue;
        return oldValue;
    }
    //returns the state (if its min or max heap)
    public String state() {
        return state;
    }
    //returns a boolean to see if its empty or not
    public boolean isEmpty() {
        return size == 0;
    }

    //returns the size
    public int size() {
        return size;
    }

    //rebuilds the heap if its max or min heap
    private void rebuildHeap() {
        for (int i = size / 2 - 1; i >= 0; i--) {
            heapifyDown(i);
        }
    }
    //it shifts the elements downward to restore the heap
    private void heapifyDown(int index) {
        while (index < size / 2) {
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            int target = index;

            //compares left and right children 
            if (leftChild < size && compare(heap[leftChild], heap[target]) < 0) {
                target = leftChild;
            }
            if (rightChild < size && compare(heap[rightChild], heap[target]) < 0) {
                target = rightChild;
            }
            if (target == index) break;

            swap(index, target);
            index = target;   //move down the index
        }
    }
    //compares two entries to see wich one is bigger or smaller
    private int compare(Entry a, Entry b) {
        return state.equals("Min") ? Integer.compare(a.key, b.key) : Integer.compare(b.key, a.key);
    }
    //swaps elements in the heap
    private void swap(int i, int j) {
        Entry temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    //extends the heap by doubling its size when its full and copy the elements into the new array
    private void extendHeap() {
        Entry[] newHeap = new Entry[heap.length * 2];
        for (int i = 0; i < heap.length; i++) {
            newHeap[i] = heap[i];
        }
        heap = newHeap;
    }
    //it shifts the elements upwards to restore the heap
    private void heapifyUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (compare(heap[index], heap[parentIndex]) < 0) {
                swap(index, parentIndex);
                index = parentIndex;
            } else {
                break;
            }
        }
    } 
    //finds the index of an entry
    private int findIndex(Entry e) {
        for (int i = 0; i < size; i++) {
            if (heap[i] == e) return i;
        }
        return -1;
    }
    
    public static void main(String[] args){
        SPQ spq1 = new SPQ(3);
        System.out.println("Empty: " + spq1.isEmpty() );         //see if its empty
        spq1.insert(5, "A");                           //add the first entry
        System.out.println("Empty: " + spq1.isEmpty() );      //it is not empty anymore
        System.out.println("\n");
        spq1.insert(2, "B");
        spq1.insert(10, "C");
        spq1.insert(3, "D");
        spq1.insert(15, "F");
        spq1.insert(13, "G");
        spq1.insert(20, "H");

        System.out.println("top: " + spq1.top());
        System.out.println("remove top: " + spq1.removeTop());
        System.out.println("new top: " + spq1.top());


        spq1.insert(1,"I");
        System.out.println("new top: " + spq1.top());
        spq1.insert(14, "J");
        System.out.println("size: " + spq1.size());

        System.out.println("\n");
        System.out.println("State before the toggle: " + spq1.state());
        spq1.toggle();
        System.out.println("State after the toggle: " + spq1.state());
        System.out.println("new top after toggle: " + spq1.top());
        spq1.toggle();
        System.out.println("State after the toggle: " + spq1.state());
        System.out.println("new top after toggle: " + spq1.top());

        System.out.println("\n");
        Entry entry1 = spq1.insert(6, "K");
        System.out.println("entry1 before replacing the key: " + entry1);
        System.out.println("Replacing the key of entry1 : " + spq1.replaceKey(entry1,0));
        System.out.println("new top after replacing the key: " + spq1.top());
        System.out.println("Replacing the value = " + spq1.replaceValue(entry1, "NEWVALUE"));
        
        System.out.println("the value has been changed: " + entry1);
        System.out.println("\n");
        System.out.println("old top: " + spq1.top());
        System.out.println("removing entry1: " + spq1.remove(entry1));
        System.out.println("new top: " + spq1.top());


    }


}
