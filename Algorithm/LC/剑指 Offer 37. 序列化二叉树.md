### 题目
请实现两个函数，分别用来序列化和反序列化二叉树。

示例: 
```
你可以将以下二叉树：

    1
   / \
  2   3
     / \
    4   5

序列化为 "[1,2,3,null,null,4,5]"
```
### 思路

不就是 层次遍历 + 一些sb的操作

### Code
```java
    public class Codec {

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            // 实际上就是个层次遍历
            if (root == null) return "[]";
            StringBuilder res = new StringBuilder("[");
            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                TreeNode cur = queue.poll();
                if (cur != null) {
                    res.append(cur.val).append(",");
                    queue.add(cur.left);
                    queue.add(cur.right);
                } else {
                    // 处理 null
                    res.append("null,");
                }
            }
            // 删掉最后那个 " , " 补上右括号
            res.deleteCharAt(res.length() - 1);
            res.append("]");
            return res.toString();
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            // 数组恢复二叉树
            if (data.equals("[]")) return null;
            String[] array = data.substring(1, data.length() - 1).split(",");
            // 把 array[0] 转成 int 放到树里
            TreeNode root = new TreeNode(Integer.parseInt(array[0]));
            Queue<TreeNode> queue = new LinkedList<>();
            // 和层次遍历类似 root进队
            queue.add(root);
            // 用来控制数组位置 因为 null 他也站位置
            int i = 1;
            while (!queue.isEmpty()) {
                // 当前 节点 队列里出去
                TreeNode cur = queue.poll();
                // 判空
                if (!array[i].equals("null")) {
                    // 拿到 left 值
                    cur.left = new TreeNode(Integer.parseInt(array[i]));
                    // left 进队
                    queue.add(cur.left);
                }
                // 数组右移
                i++;
                // 操作 right
                if (!array[i].equals("null")) {
                    cur.right = new TreeNode(Integer.parseInt(array[i]));
                    queue.add(cur.right);
                }
                i++;
            }
            return root;
        }
    }
```
*** 
### 收获
