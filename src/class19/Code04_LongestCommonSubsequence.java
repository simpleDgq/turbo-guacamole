package class19;

public class Code04_LongestCommonSubsequence {
	// 链接：https://leetcode.com/problems/longest-common-subsequence/
    /**
     * 求两个字符串的最长公共子串的长度 - 样本对应模型
     * 
     * 结尾字符做文章:
     * 1. str1只剩一个字符了，str2还有字符 （i == 0的情况）
     * 2. str2只剩一个字符了，str1还有字符 （j == 0的情况）
     * 3. str1和str2都只剩一个字符了 （i == 0 && j == 0的情况）
     * 4. str1和str2都还有多个字符 （i和j都不等于0的情况）
     * // a) 最长公共子序列，一定不以str1[i]字符结尾、也一定不以str2[j]字符结尾
     * // b) 最长公共子序列，可能以str1[i]字符结尾、但是一定不以str2[j]字符结尾
     * // c) 最长公共子序列，一定不以str1[i]字符结尾、但是可能以str2[j]字符结尾
     * // d) 最长公共子序列，必须以str1[i]字符结尾、也必须以str2[j]字符结尾
     * 
     * a是个垃圾，因为a是在str1[0...i-1]与str2[0...j-1]上求公共子串
     * 而b是在str1[0...i]与str2[0...j-1]上求，b的范围已经包含a了（
     * b求出来的最大长度一定是大于等于a情况的），没必要
     * 再去求。所以直接省略a。
     *
     */
    public int longestCommonSubsequence1(String text1, String text2) {
        if (text1 == null || text1.length() == 0 ||
                text2 == null || text2.length() == 0) {
            return 0;
        }
        int N = text1.length();
        int M = text2.length();
        char str1[] = text1.toCharArray();
        char str2[] = text2.toCharArray();
        return process(N - 1, M - 1, str1, str2);
    }

    // str1[0...i] 与str2[0...j] 范围上，最长公共子串是多少？
    public int process(int i, int j, char str1[], char[] str2) {
        if (i == 0 && j == 0) { // 如果都只剩最后一个字符了
            return str1[i] == str2[j] ? 1 : 0; // 最后一个字符相等，答案就是1，否则是0
        } else if (i == 0) { // str1只剩一个字符，str2还有字符
            if (str1[i] == str2[j]) { // 如果str1最后一个字符和str2最后一个字符相等，那么答案是1
                return 1;
            } else { // 不相等，继续去str2左边找,看有没有相等的字符
                return process(i, j - 1, str1, str2);
            }
        } else if (j == 0) { // str2只剩一个字符，str1还有字符
            if (str2[j] == str1[i]) { // 如果str2最后一个字符和str1最后一个字符相等，那么答案是1
                return 1;
            } else { // 不相等，继续去str1左边找,看有没有相等的字符
                return process(i - 1, j, str1, str2);
            }
        } else {// str1和str2都还有字符
            // 可能性1： 最长公共子序列可能以str1[i]结尾，一定不以str2[j]结尾 --> 去j-1位置搞
            // 可能性2： 最长公共子序列可能以str2[j]结尾，一定不以str1[i]结尾 --> 去i-1位置搞
            // 可能性3： 最长公共子序列一定以str1[i]结尾，也一定以str2[j]结尾(必须str1[i] == str2[j])
            int p1 = process(i, j - 1, str1, str2);
            int p2 = process(i - 1, j, str1, str2);
            int p3 = 0;
            if (str1[i] == str2[j]) {
                p3 = 1 + process(i - 1, j - 1, str1, str2); // 需要加上结尾字符
            }
            return Math.max(p1, Math.max(p2, p3));
        }
    }

    /**
     * 动态规划：
     * 可变参数： i，j
     * 可变参数范围：i： 0 ~ N - 1 j： 0 ~ M - 1 --> dp[N][M]
     * dp[i][j]含义： str1在0到i范围上，str2在0到j范围上，最长公共子序列的长度是多少？
     * 
     * 第0行：
     * dp[0][0]: str1和str2都只剩一个字符，看str1[0]是否等于str2[0]，相等则答案是1，不相等则答案是0
     * str1只剩一个字符，看它和str2[j]是否相等，如果相等，那么答案就是1，不相等，去str2的左边找
     * dp[i][j] = dp[i][j - 1]
     * 第0列：
     * str2只剩一个字符，看它和str1[i]是否相等，如果相等，答案就是1，不相等，去str1的坐标找，
     * dp[i][j] = dp[i - 1][j]
     * 普遍位置：
     * 可能性1： 最长公共子序列可能以str1[i]结尾，一定不以str2[j]结尾 --> 去j-1位置搞 dp[i][j] = dp[i][j - 1]
     * 可能性2： 最长公共子序列可能以str2[j]结尾，一定不以str1[i]结尾 --> 去i-1位置搞 dp[i][j] = dp[i - 1][j]
     * 可能性3： 最长公共子序列一定以str1[i]结尾，也一定以str2[j]结尾(必须str1[i] == str2[j]) -> dp[i][j] = 1 + dp[i - 1][j - 1]
     */
    public int longestCommonSubsequence(String text1, String text2) {
        if (text1 == null || text1.length() == 0 ||
                text2 == null || text2.length() == 0) {
            return 0;
        }
        int N = text1.length();
        int M = text2.length();
        char str1[] = text1.toCharArray();
        char str2[] = text2.toCharArray();
        int dp[][] = new int[N][M];
        dp[0][0] = str1[0] == str2[0] ? 1 : 0;
        // 填好第一行
        for (int j = 1; j <= M - 1; j++) {
            if (str1[0] == str2[j]) { //str1只剩一个字符
                dp[0][j] = 1;
            } else {
                dp[0][j] = dp[0][j - 1]; // 去str2的左边找
            }
        }
        // 填好第一列
        for (int i = 1; i <= N - 1; i++) {
            if (str2[0] == str1[i]) {//str2只剩一个字符
                dp[i][0] = 1;
            } else {
                dp[i][0] = dp[i - 1][0]; // 去str1的左边找
            }
        }
        // 从上往下，从左往右填普遍位置
        for (int i = 1; i <= N - 1; i++) {
            for (int j = 1; j <= M - 1; j++) {
                int p1 = dp[i][j - 1];
                int p2 = dp[i - 1][j];
                int p3 = 0;
                if (str1[i] == str2[j]) {
                    p3 = dp[i - 1][j - 1] + 1; // 加上相等的位置的字符，然后分别去str1和str2的左边搞
                }
                dp[i][j] = Math.max(p1, Math.max(p2, p3));// 取最大值
            }
        }
        return dp[N - 1][M - 1];
    }
}
