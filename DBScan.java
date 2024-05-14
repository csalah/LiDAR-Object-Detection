import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.*;
import java.util.Stack;



public class DBScan {

    //instances

    private List<Point3D> pts ;
    private double eps, minPts;
    private int numberOfClusters = 0; 

    //constructor
    public DBScan(List<Point3D> pts){
        this.pts = pts;
        
    }
    
    //setter for Eps
    public void setEps(double eps){
        this.eps = eps;
    }

    //setter for minimum points
    public void setMinPts(double minPts){
        this.minPts = minPts;
    }

    // returns list of pts of DBScan object 
    public List<Point3D> getPoints(){
        return pts;
    }

        //this method will read a file and on every line of the file create a new Point3D type of oject that will be added to a list
        //returns a list of Point3D objects
    public static List<Point3D> read(String filename){
        List<Point3D> temp = new ArrayList<Point3D>();
        
        try {

            String pointDataLine;
            BufferedReader reader = new BufferedReader(new FileReader(filename)); //create reader to read a file 
            reader.readLine(); //skipping headers 
            reader.readLine(); //skipping headers
            while ((pointDataLine = reader.readLine()) != null){            //reads every line
                String[] values = pointDataLine.split(",");      //we will be using csv's so we split values in the lines at commas
                double x = Double.parseDouble(values[0]);  //getting a x  value for Point3D
                double y = Double.parseDouble(values[1]);  //getting a y  value for Point3D
                double z = Double.parseDouble(values[2]);  //getting a z  value for Point3D
                Point3D newPoint = new Point3D(); //instance of new Point3D object and we use setter for values we got below 
                newPoint.setX(x);
                newPoint.setY(y);
                newPoint.setZ(z);
                temp.add(newPoint); // new object will be added to the list of Point3D objects 

            };
            reader.close(); // needed to save values read 
        } catch (IOException e) {
            e.printStackTrace();  //used to handle any errors or exceptions when I run program
        }
            return temp;
    } //end of read method

    
    public void save(String filename){
        //we create a new array of the size of the amount of clusters we have 
        //depending on how many clusters we have, we will generate a unique R,G, and B for each cluster and every index will hold
        // a certain mix of numbers between 0-1 for every letter

        String[] RGB = new String[getNumberOfClusters()];
        for(int i = 0; i< RGB.length;i++){

           Random random = new Random();

           double R = ((double) ((Math.random() * (1 - 0)) + 0));
           double G = ((double) ((Math.random() * (1 - 0)) + 0));
           double B = ((double) ((Math.random() * (1 - 0)) + 0));

           RGB[i] = Double.toString(R) + "," + Double.toString(G) + "," + Double.toString(B); 
   
       }
        try {
       
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename)); // will use a writer to write everything in file
            List<Point3D> points = this.getPoints();
            writer.write("x,y,z,C,R,G,B"); //header
            writer.write("\n"); //skipping to next line 
            for(Point3D p: points){
                if(p.getLabel() == -1){continue;} // -1 means the point was labeled as noise therefore not necessary so we skip 
                //every point will have a x,y,z,C (the label), R, G, B 
                writer.write(p.getX() + ", " + p.getY() + ", " + p.getZ() + ", " + p.getLabel() + ", " + RGB[p.getLabel() - 1]);
                writer.write("\n"); //skip after every point 
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace(); //catches any errors or exceptions 
        }
    } //end of save method

    void testRangeQueryKD(){
    
        long totalTime = 0 ;
        int n = 0; 
        for(int i = 0; i < pts.size(); i = i+10){ 
            Point3D point = pts.get(i); //we iterate through every point in the list pts of DBScan object
            if(point.getLabel() != 0){ 
                i = i + 10;
                continue; 
           }
        NearestNeighborsKD nB = new NearestNeighborsKD(pts); //new object of Nearestneighbors created to then utilize the rangeQuery method
        long startTime = System.nanoTime();
        nB.rangeQuery(point, eps);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        totalTime = totalTime + duration; 
        n++;
       } 

        totalTime = totalTime/1000000;
        System.out.println("Total runtime K-d: " + totalTime+ "ms");
        System.out.println("Total number of elements checked: " + n);
        System.out.println("Average time: " + totalTime/n+ "ms");} 

    void testRangeQueryLin(){
    
        long totalTime = 0 ;
        int n = 0; 
        for(int i = 0; i < pts.size(); i = i+10){ 
            Point3D point = pts.get(i); //we iterate through every point in the list pts of DBScan object
            if(point.getLabel() != 0){ 
                i = i + 10;
                continue; 
           }
        NearestNeighbors nB = new NearestNeighbors(pts); //new object of Nearestneighbors created to then utilize the rangeQuery method
        long startTime = System.nanoTime();
        nB.rangeQuery(point, eps);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        totalTime = totalTime + duration; 
        n++;
       } 

        totalTime = totalTime/1000000;
        System.out.println("Total runtime linear: " + totalTime+ "ms");
        System.out.println("Total number of elements checked: " + n);
        System.out.println("Average time: " + totalTime/n+ "ms");} 

    //this method locates the neighbors of every point and sets labels 
    public void findClusters(){
        for(Point3D point: pts){  //we iterate through every point in the list pts of DBScan object
            if(point.getLabel() != 0){ continue; } // if label is undefined (a value other then 0) we skip to the next object in the list

                NearestNeighbors nB = new NearestNeighbors(pts); //new object of Nearestneighbors created to then utilize the rangeQuery method
                List<Point3D> N = nB.rangeQuery(point, eps); // we find the neighbours of given point and eps required and add them to a list if they qualify
                if(N.size() < minPts){
                    point.setLabel(-1); // if the list of neighbours is not long enough (less then minPts) we set the point's label to -1 == noise
                    continue;
                } 
                
            
            
            
            numberOfClusters += 1; // increment the total number of clusters
            point.setLabel(numberOfClusters); //the point's label is changed to the current Cluster
            Stack<Point3D> S = new Stack<>(); //stack will be used to verify the neighhbours of points in the current list 

            for(Point3D n : N){ //add current point's neighbors to stack 
                S.push(n);
            }

            while(!S.isEmpty()){ //for every element of the stack of neighbors we verify the label 
                Point3D Q = S.pop();
                if(Q.getLabel() == -1){ Q.setLabel(numberOfClusters);} //if its a neighbor and noise we change it to current cluster
                if(Q.getLabel() != 0){continue;} //if already defined we skip 
                Q.setLabel(numberOfClusters); 
                N = nB.rangeQuery(Q,eps); //finiding neighbors of neighbor
                if(N.size()>= minPts){ //pushing point neighbors to stack if it meets the minimum points criteria 
                    for (Point3D point3d : N) {
                        S.push(point3d);
                    }
                }

            }
            

        }// end of for loop
        
    }//end of findClusters

        //returns total number of clusters 
    public int getNumberOfClusters(){
        return numberOfClusters;
    }
 

    public static void main(String[] args) {
        long startTime = System.nanoTime(); //looking for total runtime including all steps, starts here

        String fileName = args[0]; // the name of file will be the first in terminal
        String fileNameNofileType; // will be used to remove .csv 
        fileNameNofileType = fileName.replace(".csv", "_"); // .csv file type will be replaced with "_"
        double eps = Double.parseDouble(args[1]) ; // gets the double value of eps entered in terminal 
        int minPoints = Integer.valueOf(args[2]); //gets the double value of minPts entered in terminal

        List<Point3D> points;
        points = read(fileName) ;  //this will return a list of Point3D objects read from the file written in terminal
        DBScan dbScan = new DBScan(points); //will create a new DBScan object with current list of points 
        dbScan.setEps(eps);   //we set the eps and minPts to value given in terminal 
        dbScan.setMinPts(minPoints);
        

        dbScan.findClusters(); // we look for clusters 

       String fileNameCreated = fileNameNofileType  + "Clusters" + "_" + eps + "_" + minPoints + "_" + (dbScan.getNumberOfClusters())+ ".csv";

        dbScan.save(fileNameCreated); // save all the points in a csv file with the required info in the file name

        long endTime = System.nanoTime(); //end of all steps
        long duration = (endTime - startTime)/1000000;
        System.out.println("Total runtime DBScan: " + duration+"ms");

    
    }//end of main

} //end of DBScan class