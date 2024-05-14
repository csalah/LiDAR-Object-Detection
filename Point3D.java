

public class Point3D {

//instances 
private int label;
private double x,y,z;

public Point3D(double x, double y, double z){
    this.x = x;
    this.y = y;
    this.z = z;

}
public Point3D(){}
    // returns value of X
    public double getX(){
        return x;
    }
    // returns value of Y
    public double getY(){
        return y;
    }

    // returns value of Z

    public double getZ(){
        return z;
    }
    // returns Point3D object's label
    public int getLabel(){
        return label;
    }
    
    //setter for X
    public void setX(double data){
        this.x = data;
    }
    //setter for Y

    public void setY(double data){
        this.y = data;
    }
    //setter for Z

    public void setZ(double data){
        this.z = data;
    }
    //setter for label

    public void setLabel(int data){
        this.label = data;
    }
    //this method returns the Euclidean distance betweent two points
    public double distance(Point3D pt){
        double distance = Math.sqrt( Math.pow((pt.getX() - this.getX()), 2) + Math.pow((pt.getY() - this.getY()),2) + Math.pow((pt.getZ() - this.getZ()),2) );

        return distance;
    } //end of distance method 

    public double get(int axis){
        double value = 0.0;
        switch(axis){
            case 0:
            value = x;
            break;
            case 1:
            value = y;
            break;
            case 2:
            value = z;
            break;
        } return value;
    } 

    public String toString(){
        return "[X: " + x + " " + "Y: " + y + " " +"Z: " + z + "]";
    }
}// end Point3D class 
