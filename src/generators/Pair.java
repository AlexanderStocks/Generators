package generators;

import java.util.Objects;

public final class Pair<S, T> {

  private S left;
  private T right;

  public Pair(S left, T right) {
    this.left = left;
    this.right = right;
  }

  public S getLeft() {
    return left;
  }

  public T getRight() {
    return right;
  }

  @Override
  public String toString() {
    return "{" + left.toString() + ", " + right.toString() + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Pair<?, ?> pair = (Pair<?, ?>) o;
    return Objects.equals(left, pair.left) &&
      Objects.equals(right, pair.right);
  }

  @Override
  public int hashCode() {
    return Objects.hash(left, right);
  }
}
