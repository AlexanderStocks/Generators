package datastructures;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockableNode<E> {

  private int agentId;

  private List<E> items = new LinkedList<>();

  LockableNode<E> left;
  LockableNode<E> right;
  private Lock lock = new ReentrantLock();

  public LockableNode(int agentId) {
    this.agentId = agentId;
  }


  public void lock() {
    lock.lock();
  }

  public void unlock() {
    lock.unlock();
  }

  public int getAgentId() {
    return agentId;
  }

  public E getItem() {
    return items.remove(0);
  }


  public void addItem(E item) {
    items.add(item);
  }

  public int size () {
    return items.size();
  }

}
