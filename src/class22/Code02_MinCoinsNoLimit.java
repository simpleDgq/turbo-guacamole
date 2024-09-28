package class22;

public class Code02_MinCoinsNoLimit {
	/**
	 * 是面值数组，其中的值都是正数且没有重复。再给定一个正数aim。
	 * 每个值都认为是一种面值，且认为张数是无限的。
	 * 返回组成aim的最少货币数
	 */
    public int coinChange(int[] coins, int amount) {
        if (coins == null || coins.length == 0) {
            return -1;
        }
        int ans = dp2(coins, amount);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    // 从左往右的尝试模型
    // 当前来到index位置，index位置的面值有无数张可以用，还剩rest要搞，给我返回
    // 组成rest需要使用的最少货币数
    public int process(int index, int rest, int[] coins) {
        // 如果到了结束位置
        if (index == coins.length) {
            // rest为0，组成0不需要货币数，直接返回0，如果rest不是0，那么说明搞不定，用-1标记
            return rest == 0 ? 0 : Integer.MAX_VALUE;
        }
        int ans = Integer.MAX_VALUE;
        // index位置的钞票可以选无数张，都去尝试一下。比如选0张，选1张....之后，index+1继续搞剩下的rest
        for (int zhangs = 0; zhangs * coins[index] <= rest; zhangs++) { // 选了zhangs张arr[index]之后，不能超过rest
            int next = process(index + 1, rest - zhangs * coins[index], coins); // 剩下的rest能被搞定的最小张数
            if (next != Integer.MAX_VALUE) { // 剩下的rest能够被搞定
                ans = Math.min(ans, next + zhangs); // next + zhangs ==> 组成rest的最小张数。
            }
        }
        return ans;
    }

    // 动态规划该dp
    public int dp(int[] coins, int amount) {
        /*
         * 分析可变参数：index和rest
         * 可变参数范围：index: 0 ~ N rest: 0~amount
         * 所以dp表: new int[N+1][amount + 1]
         * 根据递归可以知道，index位置的答案只依赖于它index+1行前面的位置，所以，从下往上，从左往右填表
         */
        int N = coins.length;
        int dp[][] = new int[N + 1][amount + 1];
        // 根据base case填好最后一行
        dp[N][0] = 0;
        for (int j = 1; j <= amount; j++) { // base case最后一行其它列都搞不定
            dp[N][j] = Integer.MAX_VALUE;
        }
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= amount; rest++) {
                int ans = Integer.MAX_VALUE;
                for (int zhangs = 0; zhangs * coins[index] <= rest; zhangs++) {
                    int next = dp[index + 1][rest - zhangs * coins[index]];
                    if (next != Integer.MAX_VALUE) {
                        ans = Math.min(ans, next + zhangs);
                    }
                }
                dp[index][rest] = ans;
            }
        }
        return dp[0][amount]; // 根据主函数知道答案在哪
    }

    /**
     * 结论: dp[index][rest]依赖于它左边和下面的元素
     * dp含义：0到index位置的硬币，随便选，能够搞定rest最小需要的数量是多少
     * 依赖的左边是：dp[index][rest - arr[index]], 0到index位置的硬币，随便选，能够搞定rest-
     * arr[index]最小需要的数量
     * 为了搞定rest，所以是dp[index][rest - arr[index]] + 1, 加1表示的就是用一张arr[index]就能凑出rest了
     * 下边： dp[index + 1][rest]
     * dp[index][rest] = Math.min(dp[index][rest - arr[index]] + 1, dp[index][rest - arr[index]])
     * 
     * 上面的动态规划有枚举行为，可以分析严格依赖，进行优化
     * 主要就是举例子
     * 比如：当前遍历到的index是4，arr[index]是3，剩余的rest是14
     * 这个for会怎么求，在纸上画出来，dp[4][14]应该怎么求？
     * zhangs = 0, 那么依赖的位置是它下面的位置dp[5][14] a
     * zhangs = 1, 14 - 1 * 3 = 11, 那么依赖的位置是它下面的位置dp[5][11] b
     * zhangs = 2, 14 - 2 * 3 = 9,那么依赖的位置是它下面的位置dp[5][9] b
     * zhangs = 3, 14 - 3 * 3 = 5,那么依赖的位置是它下面的位置dp[5][5] d
     * zhangs = 4, 14 - 4 * 3 = 2,那么依赖的位置是它下面的位置dp[5][2] e
     * zhangs = 5, 14 - 5 * 3 < 0了不能继续了
     * 所以dp[4][14]是a b c d e 这5个位置中的最小值
     * 同样的分析dp[4][11], 可以得出dp[4][11] 是 b c d e 4个位置中的最小值
     * 所以dp[4][14] 只需要求它左边 + 1和下面位置的元素取最小值就行了
     */
    // 动态规划该dp
    public int dp2(int[] coins, int amount) {
        /*
         * 分析可变参数：index和rest
         * 可变参数范围：index: 0 ~ N rest: 0~amount
         * 所以dp表: new int[N+1][amount + 1]
         * 根据递归可以知道，index位置的答案只依赖于它index+1行前面的位置，所以，从下往上，从左往右填表
         */
        int N = coins.length;
        int dp[][] = new int[N + 1][amount + 1];
        // 根据base case填好最后一行
        dp[N][0] = 0;
        for (int j = 1; j <= amount; j++) { // base case最后一行其它列都搞不定
            dp[N][j] = Integer.MAX_VALUE;
        }
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= amount; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                // 这句注意：打叉的位置得是有效值。才更新
                // 否则加1之后，最大值Integer.MAX_VALUE就溢出了，变成了负的最小，导致结果不正确
                if (rest - coins[index] >= 0 && dp[index][rest - coins[index]] != Integer.MAX_VALUE) {
                    dp[index][rest] = Math.min(dp[index][rest - coins[index]] + 1, dp[index][rest]);
                }
                // 为什么加1，dp[index][rest - coins[index]]表示的是0~index任意选能够搞定rest -
                // coins[index]的最少张票数
                // 那么只需要1张coins[index]就能够搞定rest了，所以加1
            }
        }
        return dp[0][amount]; // 根据主函数知道答案在哪
    }
}
