package com.apkcore;

import java.util.Arrays;

/**
 * 给定不同面额的硬币 coins 和一个总金额 amount。编写一个函数来计算可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回 -1。
 * <p>
 * 你可以认为每种硬币的数量是无限的。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：coins = [1, 2, 5], amount = 11
 * 输出：3
 * 解释：11 = 5 + 5 + 1
 * 示例 2：
 * <p>
 * 输入：coins = [2], amount = 3
 * 输出：-1
 * 示例 3：
 * <p>
 * 输入：coins = [1], amount = 0
 * 输出：0
 * 示例 4：
 * <p>
 * 输入：coins = [1], amount = 1
 * 输出：1
 * 示例 5：
 * <p>
 * 输入：coins = [1], amount = 2
 * 输出：2
 *  
 * <p>
 * 提示：
 * <p>
 * 1 <= coins.length <= 12
 * 1 <= coins[i] <= 231 - 1
 * 0 <= amount <= 104
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/coin-change
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class _322找零钱 {
    public static void main(String[] args) {
//        int[] arr = {1,5,20,25};
//        System.out.println(test3(41));
        int[] arr = {186, 419, 83, 408};
        System.out.println(new _322找零钱().coinChange(arr, 6249));
    }

    /**
     * 完全背包问题——填满容量为amount的背包最少需要多少硬币
     *
     * dp[j]代表含义：填满容量为j的背包最少需要多少硬币
     * 初始化dp数组：因为硬币的数量一定不会超过amount，而amount <= 10^410
     * 4
     *  ，因此初始化数组值为10001；dp[0] = 0
     * 转移方程：dp[j] = min(dp[j], dp[j - coin] + 1)
     * 当前填满容量j最少需要的硬币 = min( 之前填满容量j最少需要的硬币, 填满容量 j - coin 需要的硬币 + 1个当前硬币）
     * 返回dp[amount]，如果dp[amount]的值为10001没有变过，说明找不到硬币组合，返回-1
     *
     * 链接：https://leetcode-cn.com/problems/coin-change/solution/322-ling-qian-dui-huan-dong-tai-gui-hua-e2nt7/
     * 来源：力扣（LeetCode）
     */
    public int coinChange(int[] coins, int amount) {
        // 自底向上的动态规划
        if (amount < 0) {
            return -1;
        }
        if (coins.length == 0) {
            return -1;
        }

        int[] dp = new int[amount + 1];
        int[] faces = new int[amount + 1];
        // dp[i] = Math.min(dp[i], dp[i - coin] + 1); 防止溢出
        Arrays.fill(dp, Integer.MAX_VALUE-1);
        dp[0] = 0;
        for (int coin : coins) {
            for (int i = coin; i < amount + 1; i++) {
                if(dp[i-coin]+1<dp[i]){
                    dp[i] = dp[i - coin] + 1;
                    faces[i] = coin;
                }
//                dp[i] = Math.min(dp[i], dp[i - coin] + 1);
            }
        }
        print(amount,faces);
        return dp[amount] != Integer.MAX_VALUE-1 ? dp[amount] : -1;
    }

    public int coinChange2(int[] coins, int amount) {
        // 自底向上的动态规划
        if (amount < 0) {
            return -1;
        }
        if (coins.length == 0) {
            return -1;
        }

        int[] dp = new int[amount + 1];
        int[] faces = new int[dp.length];
        dp[0] = 0;
        for (int i = 1; i < dp.length; i++) {
            int min = Integer.MAX_VALUE;
            for (int aj : coins) {
                if (i >= aj && dp[i - aj] < min) {
                    min = dp[i - aj] + 1;
                    faces[i] = aj;
                }
            }
            dp[i] = min;
        }

        if (dp[amount] == Integer.MAX_VALUE) {
            return -1;
        }
        print(amount, faces);
        return dp[amount];
    }

    /**
     * 硬币有1，5，20，25的值，求出取到n个硬币最少需要几步
     */
    private static int test1(int n) {
        if (n < 1) {
            return Integer.MAX_VALUE;
        }
        // 递归基
        if (n == 25 || n == 20 || n == 1 || n == 5) {
            return 1;
        }
        int min1 = Math.min(test1(n - 25), test1(n - 20));
        int min2 = Math.min(test1(n - 5), test1(n - 1));
        int min = Math.min(min1, min2);
        return min + 1;
    }

    private static int test2(int n) {
        if (n < 1) {
            return -1;
        }
        int[] arr = new int[n + 1];
        return tt(n, arr);
    }

    private static int tt(int n, int[] arr) {
        if (n < 1) {
            return Integer.MAX_VALUE;
        }
        // 递归基
        if (n == 25 || n == 20 || n == 1 || n == 5) {
            return 1;
        }
        if (arr[n] == 0) {
            int min1 = Math.min(tt(n - 25, arr), tt(n - 20, arr));
            int min2 = Math.min(tt(n - 5, arr), tt(n - 1, arr));
            int min = Math.min(min1, min2);
            arr[n] = min + 1;
        }
        return arr[n];
    }

    private static int test3(int n) {
        if (n < 1) {
            return -1;
        }

        int[] dp = new int[n + 1];
        int[] faces = new int[dp.length];

        for (int i = 1; i < dp.length; i++) {
            int min = Integer.MAX_VALUE;
            if (dp[i - 1] < min) {
                min = dp[i - 1];
                faces[i] = 1;
            }
            if (i >= 5 && dp[i - 5] < min) {
                min = dp[i - 5];
                faces[i] = 5;
            }
            if (i >= 20 && dp[i - 20] < min) {
                min = dp[i - 20];
                faces[i] = 20;
            }
            if (i >= 25 && dp[i - 25] < min) {
                min = dp[i - 25];
                faces[i] = 25;
            }

            dp[i] = min + 1;
        }

        print(n, faces);
        return dp[n];
    }

    private static void print(int n, int[] faces) {
        int a = n;
        System.out.print("[");
        while (a > 0) {
            System.out.print((a == n ? "" : ", ") + faces[a]);
            a -= faces[a];
        }
        System.out.print("]");
        System.out.println();
    }
}