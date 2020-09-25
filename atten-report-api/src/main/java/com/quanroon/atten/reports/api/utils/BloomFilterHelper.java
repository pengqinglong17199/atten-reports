package com.quanroon.atten.reports.api.utils;

import com.google.common.hash.Funnel;
import com.google.common.hash.Hashing;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content BloomFilter
 * @date 2020/8/21 11:02
 */
public class BloomFilterHelper<T> {

    /**指定误判率 根据实际情况来*/
    private final double fpp = 0.02d;
    /**存储的数据量*/
    public static final int expectedInsertions = 1000;
    /**元素进行hash函数算法次数*/
    private int numHashFunctions;
    /**布隆底层位bit数组大小*/
    private int bitSize;
    /**类型转换函数*/
    private Funnel<T> funnel;

    /**
    * @Description: 构建BloomFiltes实例
    * @Author: quanroon.yaosq
    * @Date: 2020/8/21 11:22
    * @Param: [funnel, expectedInsertions] expectedInsertions：
    * @Return:
    */
    public BloomFilterHelper(Funnel<T> funnel, int expectedInsertions) {
        this.funnel = funnel;
        bitSize = optimalNumOfBits(expectedInsertions, fpp);
        numHashFunctions = optimalNumOfHashFunctions(expectedInsertions, bitSize);
    }
    /**
     * @Description: value完成hash算法，拆分成散列数组值
     * @Author: quanroon.yaosq
     * @Date: 2020/8/20 20:32
     * @Param: [value]
     * @Return: int[]
     */
    public int[] hashOffset(T value) {
        int[] offset = new int[numHashFunctions];

        long hash64 = Hashing.murmur3_128().hashObject(value, funnel).asLong();
        int hash1 = (int) hash64;
        int hash2 = (int) (hash64 >>> 32);
        for (int i = 1; i <= numHashFunctions; i++) {
            int nextHash = hash1 + i * hash2;
            if (nextHash < 0) {
                nextHash = ~nextHash;
            }
            offset[i - 1] = nextHash % bitSize;
        }

        return offset;
    }

    /**
     * 计算bit数组长度
     */
    private int optimalNumOfBits(long n, double p) {
        if (p == 0) {
            p = Double.MIN_VALUE;
        }
        return (int) (-n * Math.log(p) / (Math.log(2) * Math.log(2)));
    }

    /**
     * 计算hash方法执行次数
     */
    private int optimalNumOfHashFunctions(long n, long m) {
        return Math.max(1, (int) Math.round((double) m / n * Math.log(2)));
    }
}
