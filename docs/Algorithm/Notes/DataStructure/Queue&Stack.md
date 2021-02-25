- [用 Stack 实现 Queue](#用-stack-实现-queue)
  - [题目](#题目)
  - [思路](#思路)
  - [Code](#code)
- [用 Queue 实现 Stack](#用-queue-实现-stack)
  - [题目](#题目-1)
  - [思路](#思路-1)
  - [Code](#code-1)
- [getMinStack](#getminstack)
  - [题目](#题目-2)
  - [思路](#思路-2)
  - [Code](#code-2)
- [转化问题](#转化问题)
## 用 Stack 实现 Queue

### 题目

请你仅使用两个栈实现先入先出队列。队列应当支持一般队列的支持的所有操作（push、pop、peek、empty）：

实现 MyQueue 类：
```
void push(int x) 将元素 x 推到队列的末尾
int pop() 从队列的开头移除并返回元素
int peek() 返回队列开头的元素
boolean empty() 如果队列为空，返回 true ；否则，返回 false
```

说明：
```
你只能使用标准的栈操作 —— 也就是只有 push to top, peek/pop from top, size, 和 is empty 操作是合法的。
你所使用的语言也许不支持栈。你可以使用 list 或者 deque（双端队列）来模拟一个栈，只要是标准的栈操作即可。
```

进阶：
```
你能否实现每个操作均摊时间复杂度为 O(1) 的队列？换句话说，执行 n 个操作的总时间复杂度为 O(n) ，即使其中一个操作可能花费较长时间。
```

示例：
```
输入：
["MyQueue", "push", "push", "peek", "pop", "empty"]
[[], [1], [2], [], [], []]
输出：
[null, null, null, 1, 1, false]

解释：
MyQueue myQueue = new MyQueue();
myQueue.push(1); // queue is: [1]
myQueue.push(2); // queue is: [1, 2] (leftmost is front of the queue)
myQueue.peek(); // return 1
myQueue.pop(); // return 1, queue is [2]
myQueue.empty(); // return false
```

提示：

- 1 <= x <= 9
- 最多调用 100 次 push、pop、peek 和 empty
- 假设所有操作都是有效的 （例如，一个空的队列不会调用 pop 或者 peek 操作）

来源：力扣（LeetCode）

链接：https://leetcode-cn.com/problems/implement-queue-using-stacks

### 思路

总是维护成队列的特性

注意 当 pop栈 空时 要一次性把 push栈 里的东西一次压完

### Code
```java
    class MyQueue {

        Deque<Integer> stackPop;
        Deque<Integer> stackPush;

        /**
         * Initialize your data structure here.
         */
        public MyQueue() {
            stackPop = new ArrayDeque<Integer>();
            stackPush = new ArrayDeque<Integer>();
        }

        /**
         * Push element x to the back of queue.
         */
        public void push(int x) {
            stackPush.push(x);
            trans();
        }

        /**
         * Removes the element from in front of queue and returns that element.
         */
        public int pop() {
            trans();
            return stackPop.pop();
        }

        /**
         * Get the front element.
         */
        public int peek() {
            trans();
            return stackPop.peek();
        }

        /**
         * Returns whether the queue is empty.
         */
        public boolean empty() {
            trans();
            return stackPop.isEmpty();
        }

        public void trans() {
            if (stackPop.isEmpty()) {
                while (!stackPush.isEmpty()) {
                    stackPop.push(stackPush.pop());
                }
            }
        }
    }
```

## 用 Queue 实现 Stack

### 题目

请你仅使用两个队列实现一个后入先出（LIFO）的栈，并支持普通队列的全部四种操作（push、top、pop 和 empty）。

实现 MyStack 类：
```
void push(int x) 将元素 x 压入栈顶。
int pop() 移除并返回栈顶元素。
int top() 返回栈顶元素。
boolean empty() 如果栈是空的，返回 true ；否则，返回 false 。
```

注意：
```
你只能使用队列的基本操作 —— 也就是 push to back、peek/pop from front、size 和 is empty 这些操作。
你所使用的语言也许不支持队列。 你可以使用 list （列表）或者 deque（双端队列）来模拟一个队列 , 只要是标准的队列操作即可。
```


示例：
```
输入：
["MyStack", "push", "push", "top", "pop", "empty"]
[[], [1], [2], [], [], []]
输出：
[null, null, null, 2, 2, false]

解释：
MyStack myStack = new MyStack();
myStack.push(1);
myStack.push(2);
myStack.top(); // 返回 2
myStack.pop(); // 返回 2
myStack.empty(); // 返回 False
```

提示：

- 1 <= x <= 9
- 最多调用100 次 push、pop、top 和 empty
- 每次调用 pop 和 top 都保证栈不为空

进阶：你能否实现每种操作的均摊时间复杂度为 O(1) 的栈？换句话说，执行 n 个操作的总时间复杂度 O(n) ，尽管其中某个操作可能需要比其他操作更长的时间。你可以使用两个以上的队列。

来源：力扣（LeetCode）

链接：https://leetcode-cn.com/problems/implement-stack-using-queues

### 思路

Stack FILO

### Code
```java
    class MyStack {
        Queue<Integer> queue1;
        Queue<Integer> queue2;

        /**
         * Initialize your data structure here.
         */
        public MyStack() {
            queue1 = new LinkedList<>();
            queue2 = new LinkedList<>();
        }

        /**
         * Push element x onto stack.
         */
        public void push(int x) {
            queue2.offer(x);
            while (!queue1.isEmpty()) {
                queue2.offer(queue1.poll());
            }
            Queue<Integer> temp = queue1;
            queue1 = queue2;
            queue2 = temp;

        }

        /**
         * Removes the element on top of the stack and returns that element.
         */
        public int pop() {
            return queue1.poll();
        }

        /**
         * Get the top element.
         */
        public int top() {
            return queue1.peek();
        }

        /**
         * Returns whether the stack is empty.
         */
        public boolean empty() {
            return queue1.isEmpty();
        }
    }

```

## getMinStack

### 题目

请设计一个栈，除了常规栈支持的pop与push函数以外，还支持min函数，该函数返回栈元素中的最小值。执行push、pop和min操作的时间复杂度必须为O(1)。

示例：
```
MinStack minStack = new MinStack();
minStack.push(-2);
minStack.push(0);
minStack.push(-3);
minStack.getMin();   --> 返回 -3.
minStack.pop();
minStack.top();      --> 返回 0.
minStack.getMin();   --> 返回 -2.
```

来源：力扣（LeetCode）

链接：https://leetcode-cn.com/problems/min-stack-lcci

### 思路

总体思路

两个栈 
    
压入  
        
    ori 压 当前数据 
        
    temp 每次都压 (peek, 和当前) 小的那个
    当当前比栈顶大是 还是压入同样一个栈顶元素

弹出 

    同步弹出

getMin 

    就 temp.peek() 因为他就是最小的    
    
### Code
```java
class MinStack {
        Deque<Integer> stackOri;
        Deque<Integer> stackTemp;

        /**
         * initialize your data structure here.
         */
        public MinStack() {
            this.stackOri = new ArrayDeque<>();
            this.stackTemp = new ArrayDeque<>();
        }

        public void push(int x) {
            if (this.stackTemp.isEmpty()) {
                this.stackTemp.push(x);
            } else if (x < this.getMin()) {
                this.stackTemp.push(x);
            } else {
                int min = this.stackTemp.peek();
                this.stackTemp.push(min);
            }
            this.stackOri.push(x);
        }

        public void pop() {
            this.stackTemp.pop();
            this.stackOri.pop();
        }

        public int top() {
            return stackOri.peek();
        }

        public int getMin() {
            return this.stackTemp.peek();
        }
    
```

## 转化问题

只用 栈 搞 BFS = 用 栈 搞 队列

只用 队列 搞 DFS = 用 队列 搞栈