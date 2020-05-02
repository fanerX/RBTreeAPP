package myClass;

import java.util.ArrayList;
import java.util.List;

public class RBTree {
    public static final boolean RED = true;
    public static final boolean BLACK = false;
    private List<RBNode> nodeList=new ArrayList<>();
    private List<Side> sideList=new ArrayList<>();
    private RBNode root;
    private int size;
    private int height;

    public RBTree(){
        this.root=null;
        this.size=0;
    }

    public void printTree(){
        System.out.println("\n中序遍历：");
        middleOrder(getRoot());
        System.out.println("\n先序遍历：");
        preorder(getRoot());
    }

    private void middleOrder(RBNode node){
        if(node == null){
            return;
        }
        middleOrder(node.getLeft());
        System.out.print(node.getData());
        if(node.getColor()==RED){
            System.out.print(":RED  ");
        }else {
            System.out.print(":BLACK  ");
        }
        middleOrder(node.getRight());
    }

    public void clear(){
        this.root=null;
    }
    private void preorder(RBNode node){
        if(node==null){
            return;
        }
        System.out.print(node.getData());
        if(node.getColor()==RED){
            System.out.print(":RED  ");
        }else {
            System.out.print(":BLACK  ");
        }
        preorder(node.getLeft());
        preorder(node.getRight());
    }



    public void insert(int data){
        //1.找到插入位置
        RBNode temp=getRoot();
        RBNode parent = temp;
        while (temp != null){
            parent = temp;
            if(data > temp.data){
                temp= temp.right;
            }else if(data < temp.data){
                temp=temp.left;
            }else {

                System.out.println("元素相同，去重");
                return;//元素相同，去重
            }
        }
        //2.插入
        RBNode node = new RBNode(data);
        node.setColor(RED);
        if(parent == null){
            setRoot(node);
            System.out.println("parent == null");
        }else {
            if(node.data > parent.data){
                //parent.setRight(node);
                parent.right=node;
            }else {
                //parent.setLeft(node);
                parent.left=node;
            }
            //node.setParent(parent);
            node.parent=parent;
            System.out.println("node.setParent(parent)");
        }
        //3.调整
        adjustment(node);
    }

    private void adjustment(RBNode node){
        //1.红黑树为空，将根节点染色为黑色
        getRoot().setColor(BLACK);
        //2.插入结点已经存在，不需要处理
        //3.插入结点的父结点为黑色，不需要处理
        RBNode parent=node.parent;
        if(parent==null){
            return;
        }
        if (parent.color==BLACK){
            return;
        }
        //4.插入节点的父结点为红色
        //4.1.叔叔结点存在，并且为红色（叔父双红）：将爸爸和叔叔染色为黑色，将爷爷结点染色为红色，并且再以爷爷结点为当前结点，进行下一轮处理
        RBNode pp = parent.parent;
        RBNode uncle = pp.left;
        if(pp.left==parent){
            uncle=pp.right;
        }
        if(uncle!=null&&uncle.color==RED){
            parent.color=BLACK;
            uncle.color=BLACK;
            pp.color=RED;
            adjustment(pp);
            return;
        }
        if(pp.left==parent){
            //4.2叔叔结点不存在或者为黑色，父结点为爷爷结点的左子树
            if(parent.left==node){
                //4.2.1插入结点为父结点的左子结点（LL情况）：将爸爸结点染色为黑色，将爷爷结点染色为红色，然后以爷爷结点右旋，就完成了
                parent.color=BLACK;
                pp.color=RED;
                rightRotate(pp);
            }else {
                //4.2.2插入结点为父结点的右子节点（LR情况）：以爸爸结点进行一次左旋，得到LL双红，以爸爸结点为当前结点进行下一轮处理
                leftRotate(parent);
                adjustment(parent);
            }
        }else {
            //4.3.叔叔结点不存在或者为黑色，父结点为爷爷结点的右子树
            if(parent.right==node){
                //4.3.1插入结点为父结点的右子节点（RR情况）：将爸爸结点染色为黑色，将爷爷结点染色为红色，然后以爷爷结点左旋，就完成了
                parent.color=BLACK;
                pp.color=RED;
                leftRotate(pp);
            }else {
                //4.3.2插入结点为父结点的左子结点（RL情况）：以爸爸结点进行一次右旋，得到RR双红，以爸爸结点为当前结点进行下一轮处理
                rightRotate(parent);
                adjustment(parent);
            }
        }

    }



    private void leftRotate(RBNode node){
        System.out.println("leftRotate:"+node.getData());
        RBNode right = node.right;
        RBNode parent = node.parent;
        //right.setParent(parent);
        right.parent=parent;
        if(right.left!=null){
            right.left.parent=node;
        }
        node.parent=right;
        node.right=right.left;
        right.left=node;
        if (parent!=null){
            if(parent.left==node){
                parent.left=right;
            }else {
                parent.right=right;
            }
        }else {
            setRoot(right);
        }
    }

    private void rightRotate(RBNode node){
        System.out.println("rightRotate:"+node.getData());
        RBNode left = node.left;
        RBNode parent = node.parent;
        left.parent=parent;
        node.parent=left;
        node.left=left.right;
        if(left.right!=null){
            left.right.parent=node;
        }
        left.right=node;
        if(parent!=null){
            if(parent.left==node){
                parent.left=left;
            }else {
                parent.right=left;
            }
        }else {
            setRoot(left);
        }
    }




    public void delete(int data){
        //从根搜索删除结点的位置
        RBNode node = getRoot();
        while (node!=null&&node.getData()!=data){
            if (data > node.getData()){
                node=node.getRight();
            }else {
                node=node.getLeft();
            }
        }
        //如果要删除结点不存在就return退出
        if(node==null){
            System.out.println("数据不存在："+data);
            return;
        }
        RBNode replace = null,parent = null,temp = null;
        //如果有左孩子和右孩子，则转化为只有一个孩子或没有孩子的情况
        if(node.left != null && node.right != null){
            temp=node.right;
            while (temp.left!=null)
                temp=temp.left;
            node.data = temp.data;
            node = temp;
        }
        //删除的是根，则root指向其孩子
        if(node.parent == null){
            //如果有左孩子，根指向左孩子，没有则指向右孩子（可能都为空）
            if(node.left != null){
                this.root=node.left;
            }else {
                this.root=node.right;
            }
            replace = this.root;
            if(this.root!=null){//根可能为空
                this.root.parent = null;
            }
        }else {
            //删除非根的情况
            RBNode child = null;
            //child指向其中一个孩子（可能为空）
            if(node.left!=null){
                child=node.left;
            }else {
                child=node.right;
            }
            //
            if(node.parent.left==node){
                node.parent.left=child;
            }else {
                node.parent.right=child;
            }
            if(child != null){
                child.parent = node.parent;
            }
            replace=child;
            parent=node.parent;
        }
        //如果待删除结点为红色，直接结束
        if(node.color == BLACK){
            //待删除结点为黑色结点，则需要进行调整
            deleteAdjustment(replace,parent);
        }
    }

    private void deleteAdjustment(RBNode replace,RBNode parent){
        //如果替代结点是黑色，且不是根节点，则parent一定不为null
        RBNode brother = null;
        while ((replace==null||replace.color==BLACK)&&replace!=this.root){
            if(parent.left == replace){
                brother=parent.right;
                // case1 红兄，brother涂黑，parent涂红，parent左旋，replace的兄弟改变了，变成了黑兄的情况
                if(brother.color == RED){
                    brother.color=BLACK;
                    parent.color=RED;
                    leftRotate(parent);
                    brother=parent.right;
                    if(brother==null){
//                        if(parent.left == null){
//                            parent.color=BLACK;
//                        }
                        System.out.println("brother==null");
                        break;
                    }
                }
                // 经过上面，不管进没进if，兄弟都成了黑色
                // case2 黑兄，且兄弟的两个孩子都为黑
                if ((brother.left == null || brother.left.color == BLACK) && (brother.right == null || brother.right.color == BLACK)) {
                    // 如果parent此时为红，则把brother的黑色转移到parent上
                    if(parent.color==RED){
                        parent.color=BLACK;
                        brother.color=RED;
                        System.out.println("如果parent此时为红，则把brother的黑色转移到parent上");
                        break;
                    }else {
                        brother.color=RED;
                        replace=parent;
                        parent=replace.parent;
                    }
                }else {
                    // case3 黑兄，兄弟的左孩子为红色
                    if(brother.left!=null&&brother.left.color==RED){
                        brother.left.color=parent.color;
                        parent.color=BLACK;
                        rightRotate(brother);
                        leftRotate(parent);
                        System.out.println("case3 黑兄，兄弟的左孩子为红色");
                    }else if(brother.right !=null&&brother.right.color==RED){
                        brother.color=parent.color;
                        parent.color= BLACK;
                        brother.right.color=BLACK;
                        leftRotate(parent);
                        System.out.println(" case4 黑兄，兄弟的右孩子为红色");
                    }
                    break;
                }
            }else {
                //对称位置的情况，把旋转方向反过来
                brother = parent.left;
                // case1 红兄，brother涂黑，parent涂红，parent左旋，replace的兄弟改变了，变成了黑兄的情况
                if (brother.color == RED) {
                    brother.color = BLACK;
                    parent.color = RED;
                    rightRotate(parent);
                    brother = parent.left;
                    if(brother==null){
                        break;
                    }
                }
                // 经过上面，不管进没进if，兄弟都成了黑色
                // case2 黑兄，且兄弟的两个孩子都为黑
                if ((brother.left == null || brother.left.color == BLACK)
                        && (brother.right == null || brother.right.color == BLACK)) {
                    // 如果parent此时为红，则把brother的黑色转移到parent上
                    if (parent.color == RED) {
                        parent.color = BLACK;
                        brother.color = RED;
                        break;
                    } else {// 如果此时parent为黑，即此时全黑了，则把brother涂红，导致brother分支少一个黑，使整个分支都少了一个黑，需要对parent又进行一轮调整
                        brother.color = RED;
                        replace = parent;
                        parent = replace.parent;
                    }
                } else {
                    // case3 黑兄，兄弟的左孩子为红色，右孩子随意
                    if (brother.right != null && brother.right.color == RED) {
                        brother.right.color = parent.color;
                        parent.color = BLACK;
                        leftRotate(brother);
                        rightRotate(parent);
                        // case4 黑兄，兄弟的右孩子为红色，左孩子随意
                    } else if (brother.left != null && brother.left.color == RED) {
                        brother.color = parent.color;
                        parent.color = BLACK;
                        brother.left.color = BLACK;
                        rightRotate(parent);
                    }
                    break;
                }
            }
        }
        //这里可以处理到删除结点为只有一个孩子结点的情况，如果是根，也会将其涂黑。
        if (replace != null)
            replace.color = BLACK;
    }

    public int getHeight() {
        height=0;
        traverse(getRoot(),0);
        return height;
    }

    public List<RBNode> getPosition(){
        nodeList.clear();
        sideList.clear();
        RBNode[] stack =new RBNode[20];
        int top=0;
        RBNode node=getRoot();
        if(node==null){
            return nodeList;
        }
        node.height=1;
        node.size=0;
        stack[top++]=node;
        while (top>0){
            node=stack[--top];
            nodeList.add(node);
            if(node.right!=null){
                node.right.height=node.height+1;
                node.right.size=node.size+(int)Math.pow(2,this.height-node.right.height);
                stack[top++]=node.right;
                sideList.add(new Side(node,node.right));
            }
            if(node.left!=null){
                node.left.height=node.height+1;
                node.left.size=node.size-(int)Math.pow(2,this.height-node.left.height);
                stack[top++]=node.left;
                sideList.add(new Side(node,node.left));
            }
        }
        return nodeList;
    }

    public List<Side> getSideList() {
        return sideList;
    }

    private void traverse(RBNode node, int h){
        if(node!=null){
            if(h>=height){
                height=h+1;
            }
            traverse(node.getLeft(),h+1);
            traverse(node.getRight(),h+1);
        }
    }

    public int getSize() {
        return size;
    }

    public void setRoot(RBNode root) {
        this.root = root;
    }

    public RBNode getRoot() {
        return root;
    }


    public static class Side{
        private RBNode parent;
        private RBNode son;

        public Side(RBNode parent,RBNode son){
            this.parent=parent;
            this.son=son;
        }

        public RBNode getParent() {
            return parent;
        }

        public RBNode getSon() {
            return son;
        }
    }

    public static class RBNode{
        private boolean color;
        private RBNode parent;
        private RBNode left;
        private RBNode right;
        private int data;
        private int height;
        private int size;//size指树的宽度而不是结点的个数

        public RBNode(int data){
            this.data=data;
            this.left=null;
            this.right=null;
            this.parent=null;
            this.color=true;
            this.height=0;
            this.size=0;
        }

        public int getSize() {
            return size;
        }

        public int getHeight() {
            return height;
        }

        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }

        public RBNode getLeft() {
            return left;
        }

        public RBNode getParent() {
            return parent;
        }

        public RBNode getRight() {
            return right;
        }

        public boolean getColor() {
            return color;
        }

        public void setColor(boolean color) {
            this.color = color;
        }

        public void setLeft(RBNode left) {
            this.left = left;
        }

        public void setParent(RBNode parent) {
            this.parent = parent;
        }

        public void setRight(RBNode right) {
            this.right = right;
        }
    }

}
