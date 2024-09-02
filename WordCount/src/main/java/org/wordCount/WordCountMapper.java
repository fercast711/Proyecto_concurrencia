package org.wordCount;

import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.JobConf;
public class WordCountMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {
    private final static IntWritable one = new IntWritable(1);
    private final Text word = new Text();
    private String type;
    @Override
    public void configure(JobConf job){
        this.type = job.get("map.task.type");
    }
    @Override
    public void map(LongWritable key, Text value,OutputCollector<Text,IntWritable> output, Reporter reporter) throws IOException{
        if (value == null) {
            return;
        }
        String line = value.toString();
        StringTokenizer  tokenizer = new StringTokenizer(line);

        if("WordCount".equals(type)){
            while (tokenizer.hasMoreTokens()){
                word.set(tokenizer.nextToken());
                output.collect(word, one);
            }
        } else if("PairCount".equals(type)){
            String[] words = line.split("\\s+");

            for(int i=0; i < words.length-1; i++){
                String pair = words[i] + " " + words[i+1];
                word.set(pair);
                output.collect(word, one);
            }
        }

    }
}
