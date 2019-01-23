package com.kg.wordcount;

import java.io.IOException;

import com.kg.wordcount.WordcountMapper;
import com.kg.wordcount.WordcountReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class WordcountDriver {
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

		//1:获取Job信息
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);

		//2:获取Jar包位置
		job.setJarByClass(WordcountDriver.class);

		//3:关联自定义的Mapper和Reducer
		job.setMapperClass(WordcountMapper.class);
		job.setReducerClass(WordcountReducer.class);

		//4:设置Map数据输出类型
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);

		//5:设置最终数据输出类型
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		//8:对大量小文件就行优化
		job.setInputFormatClass(CombineTextInputFormat.class);
		CombineTextInputFormat.setMaxInputSplitSize(job, 4194304);//最大4m左右
		CombineTextInputFormat.setMinInputSplitSize(job, 2097152);//最小2m左右


		//6:设置数据输入和输出文件的路径
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		//7:提交代码
		//job.submit();
		boolean result =job.waitForCompletion(true);
		System.exit(result?0:1);
	}

}
