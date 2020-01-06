package com.moxiaowei.neuralnetwork.BP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class BpDeepTest {

    public static double getRandom(double max) {
        double mark = Math.random() >= 0.5 ? 1 : -1;
        return Math.random() * 100 % max * mark;
    }

    public static double circular(double x, double r, double a, double b) {
        return Math.sqrt(Math.pow(r, 2) - Math.pow(x - a, 2)) + b;
    }

    public static double[][] target = new double[300][2];

    public static double[][] getData(){
        double[][] data = new double[300][7];
        for (int i = 0; i < 150; i++) {
            double v = getRandom(10);
            double v2 = circular(v, 10, 0, 0);
            data[i][0] = v;
            data[i][1] = v2;
            data[i][2] = Math.sin(v);
            data[i][3] = Math.sin(v2);
            data[i][4] = Math.pow(v, 2);
            data[i][5] = Math.pow(v2, 2);
            data[i][6] = v * v2;
            target[i][0] = 1;
            target[i][1] = 0;
        }

        for (int i = 150; i < 300; i++) {
            double v = getRandom(30);
            double v2 = circular(v, 30, 0, 0);
            data[i][0] = v;
            data[i][1] = v2;
            data[i][2] = Math.sin(v);
            data[i][3] = Math.sin(v2);
            data[i][4] = Math.pow(v, 2);
            data[i][5] = Math.pow(v2, 2);
            data[i][6] = v * v2;
            target[i][0] = 0;
            target[i][1] = 1;
        }

        return data;
    }

//    public double[][] getData2(){
//        double[][] data = new double[70][2];
//        for (int i = 0; i < 70; i++) {
//            double v = getRandom();
//            data[i][0] = v;
//            data[i][0] = circular(v, 30, 0, 0);
//        }
//        return data;
//    }


    public static void main(String[] args) {

        BpDeep bp = new BpDeep(new int[]{4, 8, 8, 2}, 0.015, 0.8);

        double[][] data = getData();

        double[][] target = BpDeepTest.target;

        ArrayList<String> errTotal = new ArrayList<>();

        //迭代训练
        for (int n = 0; n < 1000; n++)
            for (int i = 0; i < data.length; i++){
                if(i % 20 == 0)
                    errTotal.add(bp.train(data[i], target[i]) + "");
            }

        //根据训练结果来检验样本数据
//        for (int j = 0; j < data.length; j++) {
//            double[] result = bp.computeOut(data[j]);
//            System.out.println(Arrays.toString(data[j]) + ":" + Arrays.toString(result) + ":" + Arrays.toString(target[j]));
//        }

        //根据训练结果来预测一条新数据的分类
        double v = getRandom(10);
        double v2 = circular(v, 10, 0, 0);
        double[] x = new double[]{v, v2, Math.sin(v), Math.sin(v2), Math.pow(v, 2), Math.pow(v2, 2), v * v2};
        double[] result = bp.computeOut(x);
        System.out.println(Arrays.toString(x) + ":" + Arrays.toString(result));

        AtomicInteger index=new AtomicInteger(0);
        List<String> errTotalIndex = errTotal.stream().map((item) -> {
            return index.getAndIncrement() + "";
        }).collect(Collectors.toList());
        System.out.println(errTotalIndex.size());

    }
}