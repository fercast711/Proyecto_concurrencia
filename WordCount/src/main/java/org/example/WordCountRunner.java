package org.example;

import java.io.IOException;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;
public class WordCountRunner {
    public static void main(String[] args)throws IOException{
        if(args.length != 1){
            System.out.println("Numero de argumentos invalidos ingrese solo 1 argumento");
            return;
        }
        String output = args[0].replace(".txt","WordOutput");
        JobConf wordConf = new JobConf(WordCountRunner.class);
        wordConf.setJobName("WordCount");
        wordConf.setOutputKeyClass(Text.class);
        wordConf.setOutputValueClass(IntWritable.class);
        wordConf.setMapperClass(WordCountMapper.class);
        wordConf.setCombinerClass(WordCountReducer.class);
        wordConf.setReducerClass(WordCountReducer.class);
        wordConf.setInputFormat(TextInputFormat.class);
        wordConf.setOutputFormat(TextOutputFormat.class);
        wordConf.set("map.task.type", "WordCount");
        FileInputFormat.setInputPaths(wordConf,new Path(args[0]));
        FileOutputFormat.setOutputPath(wordConf,new Path(output));
        boolean isJobSuccessful = JobClient.runJob(wordConf).isSuccessful();

        if(!isJobSuccessful) return;

        output = args[0].replace(".txt", "PairOutput");
        JobConf pairConf = new JobConf(WordCountRunner.class);
        pairConf.setJobName("PairCount");
        pairConf.setOutputKeyClass(Text.class);
        pairConf.setOutputValueClass(IntWritable.class);
        pairConf.setMapperClass(WordCountMapper.class);
        pairConf.setCombinerClass(WordCountReducer.class);
        pairConf.setReducerClass(WordCountReducer.class);
        pairConf.setInputFormat(TextInputFormat.class);
        pairConf.setOutputFormat(TextOutputFormat.class);
        pairConf.set("map.task.type", "PairCount");
        FileInputFormat.setInputPaths(pairConf,new Path(args[0]));
        FileOutputFormat.setOutputPath(pairConf,new Path(output));
        JobClient.runJob(pairConf);
    }
}
