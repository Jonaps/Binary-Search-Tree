import java.util.Iterator;


public class BST<E extends Comparable<E>> implements Tree<E> {

    
    private static class TreeNode<E> {
        private E element;
        private TreeNode<E> left;
        private TreeNode<E> right;
        
        public TreeNode(E e) {
            element = e;
        }
            
    }
    private TreeNode<E> root;
    private int size = 0;
    
    public BST() {
        
    }
    
    public BST(E[] objects) {
        for(int i = 0; i < objects.length; i++) {
            insert(objects[i]);
        }
    }
    

    @Override
    public boolean search(E e) {
        TreeNode<E> current = root;
        
        while(current != null) {
            if(e.compareTo(current.element) < 0) {
                current = current.left;
            }
            else if(e.compareTo(current.element) > 0) {
                current = current.right;
            }
            else {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean insert(E e) {
        if(root == null) {
            root = createNewNode(e);
        }
        else {
            TreeNode<E> parent = null;
            TreeNode<E> current = root;
            while(current != null) {
                if(e.compareTo(current.element) < 0) {
                    parent = current;
                    current = current.left;
                }
                else if(e.compareTo(current.element) > 0) {
                    parent = current;
                    current = current.right;
                }
                else {
                    return false;
                }
            }
            if(e.compareTo(parent.element) < 0) {
                parent.left = createNewNode(e);
            }
            else {
                parent.right = createNewNode(e);
            }
        }
        size++;
        return true;
    }
    
    private TreeNode<E> createNewNode(E e) {
        return new TreeNode<E>(e);
    }
    
    @Override
    public void inorder() {
        inorder(root);
    }
    
    public void inorder(TreeNode<E> root) {
        if(root == null) {
            return;
        }
        inorder(root.left);
        System.out.print(root.element + " ");
        inorder(root.right);
    }
    
    @Override
    public void postorder() {
        postorder(root);
    }
    
    public void postorder(TreeNode<E> root) {
        if(root == null) {
            return;
        }
        postorder(root.left);
        postorder(root.right);
        System.out.print(root.element + " ");
    }
    
    @Override
    public void preorder() {
        preorder(root);
    }
    
    public void preorder(TreeNode<E> root) {
        if(root == null) {
            return;
        }
        System.out.print(root.element + " ");
        preorder(root.left);
        preorder(root.right);
        
    }
    
    @Override
    public int size() {
        return size;
    }
    
    @Override
    public boolean isEmpty() {
        return (size == 0);
    }
    
    public TreeNode<E> getRoot() {
        return root;
    }
    
    public java.util.ArrayList<TreeNode<E>> path(E e) {
        java.util.ArrayList<TreeNode<E>> list = new java.util.ArrayList<>();
        TreeNode<E> current = root;
        
        while(current != null) {
            list.add(current);
            if(e.compareTo(current.element) < 0) {
                current = current.left;
            }
            else if(e.compareTo(current.element) > 0) {
                current = current.right;
            }
            else {
                break;
            }
        }
        return list;
    }
    
    @Override
    public boolean delete(E e) {
        TreeNode<E> parent = null;
        TreeNode<E> current = root;
        
        while(current != null) {
            if(e.compareTo(current.element) < 0) {
                parent = current;
                current = current.left;
            }
            else if(e.compareTo(current.element) > 0) {
                parent = current;
                current = current.right;
            }
            else {
                break;
            }
        }
        
        if(current == null) {
            return false;
        }
        
        // CASE 1: Current has no left child
        if(current.left == null) {
            if(parent == null) {
                root = current.right;
            }
            else {
                if(e.compareTo(parent.element) < 0) {
                    parent.left = current.right;
                }
                else {
                    parent.right = current.right;
                }
            }
        }
        
        else {
            // Case 2: The current node has a left child
            TreeNode<E> parentOfRightMost = current;
            TreeNode<E> rightMost = current.left;
            
            while(rightMost.right != null) {
                parentOfRightMost = rightMost;
                rightMost = rightMost.right;
            }
            
            current.element = rightMost.element;
            
            if(parentOfRightMost.right == rightMost) {
                parentOfRightMost.right = rightMost.left;
            }
            else {
                parentOfRightMost.left = rightMost.left;
            }
        }
        
        size--;
        return true;
    }

    @Override
    public Iterator<E> iterator() {
        return new InorderIterator();
    }


    private class InorderIterator implements java.util.Iterator<E> {
        private java.util.ArrayList<E> list = new java.util.ArrayList<>();
        private int current = 0;
       
        public InorderIterator() {
            inorder();
        }
        
        private void inorder() {
            inorder(root);
        }
        
        private void inorder(TreeNode<E> root) {
            if(root == null) {
                return;
            }
            inorder(root.left);
            list.add(root.element);
            inorder(root.right);
        
        }
        @Override
        public boolean hasNext() {
            if(current < list.size()) {
                return true;
            }
            return false;
        }
        
        @Override
        public E next() {
            return list.get(current++);
        }
        
        @Override
        public void remove() {
            if(current == 0) {
                throw new IllegalStateException();
            }
            delete(list.get(--current));
            list.clear();
            inorder();
        }    
    }
    
    @Override
    public void clear() {
            root = null;
            size = 0;
    }
}
