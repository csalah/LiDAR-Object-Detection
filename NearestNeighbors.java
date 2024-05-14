import java.util.ArrayList;
import java.util.List;

public class NearestNeighbors {
    //instance of a Point3D objects list 
    private List<Point3D> n;

    public NearestNeighbors(List<Point3D> n){ //constructor with Point3D list
        this.n = n;
    }

    public List<Point3D> rangeQuery(Point3D point,double eps){  //method to identify neighbors of a given point in parameter and returns a list of points
    
        List<Point3D> temp = new ArrayList<>(); // will add neighbors to this list
       
    for(Point3D v : n){
            if (point.distance(v) <= eps ) { //for every point in NearestNeighbor object's list we will verify if it meets the distance criteria 
                temp.add(v); //to the list we add the points that qualify 
            } 
        } return temp;
    }// end of rangeQuery method

    
}// end of NearestNeighbors class
