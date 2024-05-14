
public class KDtree {

    public class KDnode {
        //instances 
        public Point3D point;
        public int axis;
        public double value;
        public KDnode left;
        public KDnode right;

        public KDnode(Point3D pt, int axis) {
        this.point= pt;
        this.axis= axis;
        this.value= pt.get(axis);
        left= right= null; }
        
        } //end of KDnode inner class

        //instances KDtree

        private KDnode root;


        public KDtree() {
        root= null; }//empty tree constructor



        public KDnode getRoot(){
            return root;

        }

        //Method to add nodes into the kdTree. This add method calls the insert method.

        public void add(Point3D p){
            if(root == null){
                root = new KDnode(p, 0);
            } else{
                int axis = getRoot().axis;
                insert(p,root,axis);
            }
        }


        public KDnode insert(Point3D p , KDnode node, int axis) {

                if (node == null) {node = new KDnode(p, axis);}

                else if (p.get(axis) <= node.value) {node.left = insert (p, node.left, (axis+1) % 3);}

                else {node.right = insert (p, node.right, (axis+1) % 3);}

            return node;
            
        }// end of insert method







}
