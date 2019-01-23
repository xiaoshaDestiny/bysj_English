package com.kg.wordcount;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * 输入的key   : LongWritable   行号
 * 输入的value : Text           一行的内容
 * 输出的key   : Text           单词
 * 输出的value : IntWritable    单词的个数
 *
 * 参数是要经过序列化的对象类型
 * @author xiaosha
 */
public class WordcountMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
	Text k = new Text();
	IntWritable v = new IntWritable(1);
	/**
	 * 处理输入文件的一行一行数据
	 */
	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		//1:将一行内容转换为String
		String line = value.toString();
		//2:切割
		String[] words = line.split(" ");
		//3:循环写出到下一个阶段
		for(String word : words){
			k.set(word);
			context.write(k,v); //注意输出的类型
		}
	}
}
