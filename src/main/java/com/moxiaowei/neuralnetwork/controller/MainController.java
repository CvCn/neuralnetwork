package com.moxiaowei.neuralnetwork.controller;

import com.moxiaowei.neuralnetwork.BP.BpDeep;
import com.moxiaowei.neuralnetwork.BP.BpDeepTest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


import static com.moxiaowei.neuralnetwork.BP.BpDeepTest.*;

@CrossOrigin(origins = {"http://localhost:8080", "null"})
@RestController
public class MainController {

    @GetMapping("/train")
    public Object tran() {

        HashMap<String, Object> re = new HashMap<>();

        BpDeep bp = new BpDeep(new int[]{4, 8, 8, 2}, 0.015, 0.8);

        double[][] data = getData();

        double[][] target = BpDeepTest.target;

        ArrayList<String> errTotal = new ArrayList<>();

        //迭代训练
        for (int n = 0; n < 500; n++) {
            double train = 0;
            for (int i = 0; i < data.length; i++) {
                train += bp.train(data[i], target[i]);
            }
            errTotal.add(Double.toString(train / data.length));
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


        AtomicInteger index = new AtomicInteger(0);
        List<String> errTotalIndex = errTotal.stream().map((item) -> index.getAndIncrement() + "").collect(Collectors.toList());

        re.put("data", data);
        re.put("errTotal", errTotal);
        re.put("errTotalIndex", errTotalIndex);
        return re;
    }
}
