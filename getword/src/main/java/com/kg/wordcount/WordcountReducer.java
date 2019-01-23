package com.kg.wordcount;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
/**
 * Reduce阶段   聚合
 * 输入的key是Map阶段的输出的key
 * 输入的value是map阶段输出的value
 * 输出的key是  单词
 * 输出的value 是单词出现的个数
 * @author Administrator
 */
public class WordcountReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
	//IntWritable fsum = new IntWritable();

	// <hello 1>
	// <hello 1>
	// <hello 1>
	@Override
	protected void reduce(Text key, Iterable<IntWritable> values,Context context)
			throws IOException, InterruptedException {
		//1:汇总统计单词总个数
		int sum = 0;
		for(IntWritable count : values){
			sum +=count.get();
		}
		//2:输出单词总个数
		context.write(key, new IntWritable(sum));
		//		fsum.set(sum);
		//		context.write(key, fsum);
	}

}
