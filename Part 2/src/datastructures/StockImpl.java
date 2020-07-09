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
    LockableNode<E> curr;
    LockableNode<E> parent;

    rootLock.lock();
    if (root == null) {
      root = new LockableNode<>(agent.id);
      root.addItem(item);
      rootLock.unlock();

    } else {
      curr = root;
      curr.lock();
      rootLock.unlock();
      int compare;

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
        parent.left = new LockableNode<>(agent.id);
        parent.left.addItem(item);
      } else {
        parent.right = new LockableNode<>(agent.id);
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

  @Override
  public int size() {
    return size.get();
  }

}
