package datastructures;

import domain.Agent;
import domain.producttypes.ExchangeableGood;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StockImpl<E extends ExchangeableGood> implements Stock<E> {

  private Node<E> root = null;

  private LockableNode<E> rootNode = null;
  private Lock rootLock = new ReentrantLock();

  private AtomicInteger size = new AtomicInteger(0);

  @Override
  public synchronized void push(E item, Agent agent) {
    push(item, agent, root);
    size.incrementAndGet();
  }

  /* This is the fine grained version
  @Override
  public synchronized void push(E item, Agent agent) {
    addId(item, agent);
    size.incrementAndGet();
  }
  */

  private void push(E item, Agent agent, Node<E> subtree) {
    if (root == null) {
      root = new Node<>(agent, item);
    } else {
      if (subtree.key() == agent.id) {
        subtree.items.add(item);
      } else if (agent.id < subtree.key()) {
        if (subtree.left == null) {
          subtree.left = new Node<>(agent, item);
        } else {
          push(item, agent, subtree.left);
        }
      } else {
        if (subtree.right == null) {
          subtree.right = new Node<>(agent, item);
        } else {
          push(item, agent, subtree.right);
        }
      }
    }
  }



  private void addId(E item, Agent agent) {
    LockableNode<E> curr;
    LockableNode<E> parent;

    rootLock.lock();
    if (rootNode == null) {
      rootNode = new LockableNode<>(agent.id);
      rootNode.addItem(item);
      rootLock.unlock();

    } else {
      curr = rootNode;
      curr.lock();
      rootLock.unlock();
      int compare;

      while (true) {
        parent = curr;

        compare = Integer.compare(curr.getAgentId(), agent.id);
        if (compare == 0) {
          curr.addItem(item);
          curr.unlock();
          break;
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
        parent.left = new LockableNode<>(agent.id);
        parent.left.addItem(item);
      } else {
        parent.right = new LockableNode<>(agent.id);
        parent.right.addItem(item);
      }
      parent.unlock();
    }
    size.incrementAndGet();
  }

  @Override
  public synchronized Optional<E> pop() {
    if (root == null) {
       return Optional.empty();
       }
     Node<E> previous = null;
     Node<E> current = root;
     while (current.right != null) {
       previous = current;
       current = current.right;
       }
     E item = current.items.get(0);
     current.items.remove(0);
     if (current.items.isEmpty()) {
       if (previous != null) {
         previous.right = current.left;
          } else {
          root = root.left;
          }
        }
     size.decrementAndGet();
     return Optional.of(item);
  }

  /* This is the fine grained version
  @Override
  public synchronized Optional<E> pop() {

    if (root == null) { // empty tree
      return Optional.empty();
    }

    LockableNode<E> curr = root;
    LockableNode<E> prev = null;

    while (curr.right != null) {
      prev = curr;
      curr = curr.right;
    }

    E toReturn = curr.getItem();

    if (curr.size() == 0) {
      if (prev != null) {
        prev.right = curr.left;
      } else {
        root = root.left;
      }
    }
    size.decrementAndGet();
    return Optional.of(toReturn);
  }
  */

  @Override
  public int size() {
    return size.get();
  }

  private static class Node<E> {
    private Agent agent;
    private List<E> items;
    private Node<E> left, right;

    public Node(Agent agent, E firstItem) {
      this.agent = agent;
      this.items = new ArrayList<>();
      items.add(firstItem);
    }

    private int key() {
      return agent.id;
    }
  }
}
