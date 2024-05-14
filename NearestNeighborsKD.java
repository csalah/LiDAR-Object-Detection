import java.util.ArrayList;
import java.util.List;


public class NearestNeighborsKD {
    //instance of a Point3D objects list 
    private KDtree kdTree;

    public NearestNeighborsKD(List<Point3D> n){ //constructor with Point3D list which we will use to create KDtree
        this.kdTree = new KDtree();
        for (Point3D p : n){
            kdTree.add(p); 
        }
    }


    public List<Point3D> rangeQuery(Point3D p, double eps){
        List<Point3D> neighbors = new ArrayList<>();
        rangeQuery(p,eps,neighbors,kdTree.getRoot());
        return neighbors;
    }
    

    void rangeQuery(Point3D P, double eps, List<Point3D> N, KDtree.KDnode node) {
        if (node == null){return;}

        if (P.distance(node.point) < eps){
        N.add(node.point);}
                        
        if (P.get(node.axis) - eps <= node.value){
        rangeQuery(P, eps, N, node.left);}

        if (P.get(node.axis) + eps > node.value){
        rangeQuery(P, eps, N, node.right);}

        return;
        }

   

    

    
}// end of NearestNeighbors class
