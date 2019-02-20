package com.kg.xiaosha.bysj_english.util;


import java.io.*;

/**
 * 本程序是将文本里面的单词变成数据库语句的第一步，因为无法在IO流中同时开启多个数据流，所以分两步来吧
 */
public class WordSQLStep1 {
    public static void main(String args[]) throws IOException {
    	//将SQL语句写入文件
		InputStreamReader  isr = new InputStreamReader(new FileInputStream("E:\\wordinput01\\wordtoutf8.txt"), "utf-8");
		BufferedReader br = new BufferedReader(isr);
		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("E:\\wordinput01\\output.txt"),"utf-8");
		BufferedWriter out = new BufferedWriter(osw); 
		
		String  line = "";					//正在操作的这行数据
		String sql ="";						//要写入的SQL语句
		Word word = new Word(); 			//这个傻屌变量，完全可以不用的
		
		int maxNum = getMaxNum();			//获取总的有几行
		String options ="暂无;暂无;暂无";
		
		
		for(int i =0 ;i<maxNum;i++) {
			line = br.readLine();System.out.println(i);System.out.println(line);
			String[] items =  line.split("/");
			word.setWord(items[0]);
			word.setPronounce(items[1]);
			word.setAnswer(items[2]);
			word.setLevel(2);
			word.setOptions(options);
	    	sql ="INSERT INTO question (word,answer,options,level,pronounce) VALUE ('"+word.getWord()+"','"+word.getAnswer()+"','"+word.getOptions()+"',2,'"+word.getPronounce()+"');";
			System.out.println(sql);
	    	out.write(sql);
			out.newLine();
		}
		
		out.close();
		osw.close(); 
		br.close();
		isr.close();
    }
    
    
    //获取文本的最大行数
    public static int getMaxNum() throws IOException {
    	InputStreamReader  isr = new InputStreamReader(new FileInputStream("E:\\wordinput01\\wordtoutf8.txt"), "utf-8");
		BufferedReader br = new BufferedReader(isr);
    	int linenumber = 0;
    	while (br.readLine() != null){
        	linenumber++;
        }
		br.close();
		isr.close();
    	return linenumber;
    }
    
    
}



