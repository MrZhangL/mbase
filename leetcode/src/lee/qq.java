package lee;

public class qq {

    public static void up(int[] heap, int index) {
        int parent = (index-1)>>1;
        while(index > 0){
            if(heap[index] < heap[parent]){
                swap(heap, index, parent);
                index = parent;
                parent = (index-1)>>1;
            } else {
                break;
            }
        }
        checkHeap(heap);
    }

    public static void swap(int[] heap, int i1, int i2) {
        int tmp = heap[i1];
        heap[i1] = heap[i2];
        heap[i2] = tmp;
    }

    public static void checkHeap(int[] heap){
        for(int i = 0; (i<<1 + 1) < heap.length; i++) {
            if(heap[i<<1 + 1] < heap[i] ) {
                System.out.println("heap error");
            }
            if(i<<1 + 2 < heap.length && heap[i<<1 + 2] < heap[i+1]){
                System.out.println("heap error");
            }
        }
    }
}
