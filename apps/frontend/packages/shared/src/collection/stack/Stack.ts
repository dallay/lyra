export interface Stack<T> {
  push(item: T): void;
  pop(): T | undefined;
  peek(): T | undefined;
  isEmpty(): boolean;
  size(): number;
}

export class ArrayStack<T> implements Stack<T> {
  private storage: T[] = [];

  push(item: T): void {
    this.storage.push(item);
  }

  pop(): T | undefined {
    return this.storage.pop();
  }

  peek(): T | undefined {
    return this.storage[this.storage.length - 1];
  }

  isEmpty(): boolean {
    return this.storage.length === 0;
  }

  size(): number {
    return this.storage.length;
  }
}
