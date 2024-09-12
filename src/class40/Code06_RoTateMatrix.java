package class40;

//https://leetcode.cn/problems/rotate-image/
public class Code06_RoTateMatrix {
    public void rotate(int[][] matrix) {
        /**
         * 看题解
         * 以位于矩阵四个角点的元素为例，设矩阵左上角元素 A 、右上角元素 B 、
         * 右下角元素 C 、左下角元素 D 。矩阵旋转 90º 后，相当于依次先后执行
         * D→A , C→D , B→C , A→B 修改元素
         * 
         * n = 4的时候，一轮可以完成矩阵 4 个元素的旋转。因而，只要4次旋转就能完成。
         * 分别以矩阵左上角 1/4 的各元素为起始点执行以上旋转操作，即可完整实现矩阵旋转。
         * 
         * 当矩阵大小 n 为偶数时，取前n/2行，前n/2列的元素为起始点
         * 当矩阵大小 n 为奇数时，取前n/2行，前(n+ 1)/2列的元素为起始点
         * 
         * 
         * 怎么推算B,C,D下标？
         * 
         * 矩阵顺时针旋转 90º 后，可找到以下规律：
         * 
         * 「第 i 行」元素旋转到「第 n−1−i 列」元素；
         * 「第 j 列」元素旋转到「第 j 行」元素；
         * 
         * A 位置(i,j) 它会去到B位置，根据上面的结论
         * 可以知道B位置的坐标是(j , n-1-i)
         * B位置是(j , n-1-i)，根据上面的结论
         * 可以指导C位置的坐标是(n-i-1, n-j-1)
         * C位置是(n-i-1, n-j-1)，根据上面的结论
         * 可以指导D位置的坐标是(n-j-1, i)
         */
        int n = matrix.length;
        for (int i = 0; i < n / 2; i++) { // 前n/2行
            for (int j = 0; j < (n + 1) / 2; j++) { // 前(n +1)/2列，偶数和奇数除法之后没区别。例如3和4，(n +1)/2都是等于2
                int temp = matrix[i][j];
                matrix[i][j] = matrix[n - j - 1][i];
                matrix[n - j - 1][i] = matrix[n - i - 1][n - j - 1];
                matrix[n - i - 1][n - j - 1] = matrix[j][n - i - 1];
                matrix[j][n - i - 1] = temp;
            }
        }
    }
}
