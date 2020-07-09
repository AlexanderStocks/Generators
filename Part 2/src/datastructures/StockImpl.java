package datastructures;

import domain.Agent;
import domain.producttypes.ExchangeableGood;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StockImpl<E extends ExchangeableGood> implements Stock<E> {

  private LockableNode<E> root = null;
  private Lock rootLock = new ReentrantLock();

  private AtomicInteger size = new AtomicInteger(0);

  @Override
  public void push(E item, Agent agent) {
    addId(item, agent);
    size.incrementAndGet();

  }

  private boolean addId(E item, Agent agent) {
    LockableNode<E> curr = null;
    LockableNode<E> parent = null;

    rootLock.lock();
    if (root == null) {
      root = new LockableNode<E>(agent.id);
      root.addItem(item);
      rootLock.unlock();

    } else {
      curr = root;
      curr.lock();
      rootLock.unlock();
      int compare = 0;

      while (true) {
        parent = curr;

        compare = Integer.compare(curr.getAgentId(), agent.id);
        if (compare == 0) {
          curr.addItem(item);
          curr.unlock();
          return false;
        } else {
          curr = (compare > 0) ? curr.left : curr.right;
        }

        if (curr == null) {
          break;
        } else {
          curr.lock();
          parent.unlock();
        }
      }

      if (compare > 0) {
        parent.left = new LockableNode<E>(agent.id);
        parent.left.addItem(item);
      } else {
        parent.right = new LockableNode<E>(agent.id);
        parent.right.addItem(item);
      }
      parent.unlock();
    }
    return true;
  }


  @Override
  public synchronized Optional<E> pop() {
    // Hint: always returns a product from the highest priority node. If a node gets to zero
    // products, it should be removed. Because this structure is a BST with nodes sorted by
    // agent.id,
    // the highest priority node should be the rightmost node, which can only be either a leaf or a
    // node with a single child (the left one).


    LockableNode<E> curr = null;
    LockableNode<E> parent = null;

    LockableNode<E> biggest = null;

    rootLock.lock();
    if (root == null) { // empty tree

      return Optional.empty();

    } else {

      curr = root;
      parent = curr;

      while (curr.right != null) {
        parent = curr;
        curr = curr.right;
      }
      E toReturn = curr.getItem();
      if (curr.size() == 0) {
        remove(curr.getAgentId());
        size.decrementAndGet();
      }
      return Optional.of(toReturn);
    }
  }

  @Override
  public int size() {
    // Hint: it is just an integer that needs incrementing/decrementing...
    // TODO Q1/Q3
    return size.get();
  }

  ////////////////////////////////////////////// helper
  // stuff////////////////////////////////////////////

  private boolean remove(int agentId) {
    LockableNode<E> curr = root;
    LockableNode<E> parent = null;

    while (Integer.compare(curr.getAgentId(), agentId) != 0) {
      parent = curr;
      if (Integer.compare(curr.getAgentId(), agentId) > 0) {
        curr = curr.left;
      } else {
        curr = curr.right;
      }
    }

    if (curr == null) {
      return false; // the node is not in
    }

    if (parent == null) {
      //remove the root
      root = deleteNode(root);
    } else if (Integer.compare(curr.getAgentId(), agentId) > 0) {
      parent.left = deleteNode(parent.left);
    } else {
      parent.right = deleteNode(parent.right);
    }
    return true;
  }


  private LockableNode<E> deleteNode(LockableNode<E> node) {
    if(node.right!=null) {
      LockableNode<E> replacementNode = findMinNode(node.right);
      replacementNode.right = removeMinNode(node.right);
      replacementNode.left = node.left;
      return replacementNode;
    }else if (node.left!=null){
      LockableNode<E> replacementNode = findMaxNode(node.left);
      replacementNode.right = node.right;
      replacementNode.left = removeMaxNode(node.left);;
      return replacementNode;
    }
    return null; //leaf node
  }

  private LockableNode<E> findMinNode(LockableNode<E> subtree) {
    LockableNode<E> curr = subtree;
    while (curr.left != null) {
      curr = curr.left;
    }
    return curr;
  }

  private LockableNode<E> findMaxNode(LockableNode<E> subtree) {
    LockableNode<E> curr = subtree;
    while (curr.right != null) {
      curr = curr.right;
    }
    return curr;
  }

  private LockableNode<E> removeMinNode(LockableNode<E> subtree) {
    assert subtree!=null;

    LockableNode<E> curr = subtree;
    LockableNode<E> parent = null;
    while (curr.left != null) {
      parent = curr;
      curr = curr.left;
    }
    if (parent == null) {
      return curr.right;
    } else {
      parent.left = curr.right;
      return subtree;
    }
  }

  private LockableNode<E> removeMaxNode(LockableNode<E> subtree) {
    assert subtree!=null;

    LockableNode<E> curr = subtree;
    LockableNode<E> parent = null;
    while (curr.right != null) {
      parent = curr;
      curr = curr.right;
    }
    if (parent == null) {
      return curr.left;
    } else {
      parent.right = curr.left;
      return subtree;
    }
  }
}
