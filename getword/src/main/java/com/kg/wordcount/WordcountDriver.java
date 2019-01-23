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

		//1:��ȡJob��Ϣ
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);

		//2:��ȡJar��λ��
		job.setJarByClass(WordcountDriver.class);

		//3:�����Զ����Mapper��Reducer
		job.setMapperClass(WordcountMapper.class);
		job.setReducerClass(WordcountReducer.class);

		//4:����Map�����������
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);

		//5:�������������������
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		//8:�Դ���С�ļ������Ż�
		job.setInputFormatClass(CombineTextInputFormat.class);
		CombineTextInputFormat.setMaxInputSplitSize(job, 4194304);//���4m����
		CombineTextInputFormat.setMinInputSplitSize(job, 2097152);//��С2m����


		//6:�����������������ļ���·��
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		//7:�ύ����
		//job.submit();
		boolean result =job.waitForCompletion(true);
		System.exit(result?0:1);
	}

}
