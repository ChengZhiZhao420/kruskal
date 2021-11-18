import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class kruskal {
    private int size;
    private int[][] edges;

    /**
     * need a file name as a parameter
     * @throws FileNotFoundException
     */
    public kruskal() throws FileNotFoundException {
        Scanner sc = new Scanner(new BufferedReader(new FileReader("C:/Users/13411/IdeaProjects/untitled2/src/test.txt")));//put any file name
        String[] line = sc.nextLine().trim().split(" ");

        this.size = line.length;//determine how many nodes from the text file
        edges = new int[size][size];

        //initialize edges array, convert the text file into the array
        for(int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                edges[i][j] = Integer.parseInt(line[j]);
            }
            if(sc.hasNext()){
                line = sc.nextLine().trim().split(" ");
            }
        }
    }

    /**
     * solve the minimum spanning tree
     */
    public void solve(){
        ArrayList des = new ArrayList(), source = new ArrayList(), weight = new ArrayList();//dynamically record the data
        boolean[][] visited = new boolean[size][size];//use for avoid reading the duplicate edge
        int[] parent = new int[size];
        int numberTree = size;
        int k = 0;
        int totalWeight = 0;

        //To sort the 2D array, it needs to convert into a 1D array, so des, source, weight will represent destination, source, and weight of that edge.
        for(int i = 0; i < size; i++){
            parent[i] = i;//initialize the parent array
            for (int j = 0; j < size; j++){
                if(edges[i][j] != -1 && !visited[i][j] && edges[i][j] != 0){//if the weight is equal 0 or -1, means there do not have an edge between two nodes. Also, if the edge is already recorded, it will not record it again
                    source.add(j);
                    des.add(i);
                    weight.add(edges[i][j]);
                    visited[i][j] = true;
                    visited[j][i] = true;
                }
            }
        }
        int[] position = new int[weight.size()];
        for(int q = 0; q < position.length; q++){
            position[q] = q;
        }
        mergesort(position, weight, 0, weight.size() - 1);//sort the edge base on its weight. the position will represent the order of the sorted edge

        while(numberTree > 1){
            int set1 = find((Integer) des.get(position[k]), parent);
            int set2 = find((Integer) source.get(position[k]), parent);

            if(set1 != set2){
                union(parent, set1, set2);
                numberTree--;
                System.out.println(source.get(position[k]) + "---------------" + des.get(position[k]));
                totalWeight += (Integer)weight.get(position[k]);
                k++;

            }
            else{
                k++;
                continue;
            }
        }
        System.out.println("The total weight is: " + totalWeight);
    }

    private void union(int[] parent, int v1, int v2){
        int set1 = find(v1, parent);
        int set2 = find(v2, parent);

        if(set1 < set2){
            parent[set2] = set1;
        }
        else{
            parent[set1] = set2;
        }
    }

    private int find(int vertex, int[] parent){
        if(parent[vertex] == vertex){
            return vertex;
        }
        else{
            return find(parent[vertex], parent);
        }
    }

    private void mergesort(int[] position, ArrayList weight, int low, int high) {
        if(low < high){
            int mid = (low + high) / 2;
            mergesort(position, weight, low, mid);
            mergesort(position, weight, mid + 1, high);
            merge(position, weight, low, mid, high);
        }

    }

    private void merge(int[] position, ArrayList weight, int low, int mid, int high) {
        int i = low, j = mid + 1, k = low;
        int[] u = new int[position.length];

        //compare two edges's weight
        while(i <= mid && j <= high){
            int weight1 = (int) weight.get(position[i]);
            int weight2 = (int) weight.get(position[j]);
            if(weight1 < weight2){
                u[k] = position[i];
                i++;
            }
            else{
                u[k] = position[j];
                j++;
            }
            k++;
        }

        if(i > mid){
            while(j <= high){
                u[k] = position[j];
                k++;
                j++;
            }
        }
        else{
            while(i <= mid){
                u[k] = position[i];
                i++;
                k++;
            }
        }

        for(int q = low; q <= high; q++){
            position[q] = u[q];
        }

    }
}
