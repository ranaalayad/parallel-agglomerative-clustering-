package com.test.agglomerative;

import java.io.*;
import java.util.*;
 import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class agglomerative {
	  static List<String> matrix = new ArrayList<String>();
   
   public static class MyMap extends Mapper<Object, Text, Text,IntWritable>{

         private Text name = new Text();
	 private Text word= new Text();
	 private DoubleWritable result = new DoubleWritable();
	List<points> p = new ArrayList<points>();
	List<String> allp = null;
	
	

	public void setup(Context context){
	Configuration conf = context.getConfiguration();
	 allp = new ArrayList<String>(Arrays.asList(conf.getStrings("allp")));
	for(int j=0;j<allp.size();j++){
	points p1=new points();
	p1.setN(allp.get(j++));
	p1.setx(Integer.parseInt(allp.get(j++)));
	p1.sety(Integer.parseInt(allp.get(j)));
	p.add(p1);
	}}
    
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
	String line = value.toString();
         StringTokenizer tokenizer = new StringTokenizer(line);
         while (tokenizer.hasMoreTokens()) {
	String[] arr = tokenizer.nextToken().split(",",2);
        word.set(arr[0]);
	 points p2 = new points();
 	 for(points p3: p){
	if(p3.getN().equals(word.toString())){
	p2=p3;}}
	for(points p4: p){
	if(!p4.getN().equals(p2.getN())){
	result=new DoubleWritable(Math.sqrt((p4.getx()-p2.getx())*(p4.getx()-p2.getx())+(p4.gety()-p2.gety())*(p4.gety()-p2.gety())));
        name.set(new Text(p2.getN()+"&"+p4.getN()));
         matrix.add(name.toString()+","+result.toString());
	name.set(new Text(name+"   "+result.toString()));
	context.write(name, new IntWritable(0));
	}}}
	
}}
	
   

  public static class Reduce extends Reducer<Text, IntWritable, Text, IntWritable> {

      public void reduce(Text key, Iterable<IntWritable> values, Context context)
         throws IOException, InterruptedException {
        
         context.write(key, new IntWritable(1));
      }
   }
    public static void main(String[] args) {
        // TODO code application logic here
	try{
     System.out.println("heellllooo");
	 
         long startTime;
        long endTime;
        long duration;
	String line;
	String p;
	 
	 int iteration = 1;
	ArrayList<String> list = new ArrayList<String>();
	List<points> Ps = new ArrayList<points>();
	List<String> Read = new ArrayList<String>();
	 Configuration conf = new Configuration();
        startTime = System.nanoTime();
    
   
    File file = new File("/home/rana/Desktop/HAC/input.txt");
    Scanner input = new Scanner(file); 
   while(input.hasNext()){
        line = input.next();
           Read.add(line); }
        for(int i=0;i<Read.size();i++){
          p=Read.get(i);
	points pf=new points();
	  list.add(p);
	String[] SS=p.split(",",3);
	  pf.setN(SS[0]);
	  pf.setx(Integer.parseInt(SS[1]));
	  pf.sety(Integer.parseInt(SS[2]));
	  Ps.add(pf);
	  }
	String[] arr = list.toArray(new String[list.size()]);
	  conf.setStrings("allp",arr);
	  
	
	
	//conf.set("mapred.max.split.size", "94");// if the input file is small, to make it to split we define the number of split here
	conf.set("mapreduce.map.cpu.vcores","2");  
    Job job = Job.getInstance(conf, "agglomerativet");
    job.setJarByClass(agglomerative.class);
    job.setMapperClass(MyMap.class);
    job.setCombinerClass(Reduce.class);
    job.setReducerClass(Reduce.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(IntWritable.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    //System.exit(job.waitForCompletion(true) ? 0 : 1);
	job.waitForCompletion(true); 
	
	List<pd> pointd = new ArrayList<pd>();
         List<cluster> cl = new ArrayList<cluster>();
         List<points> exist = new ArrayList<points>();

	
	for(String s:matrix){
	pd dp=new pd();
	points n1=new points();
	points n2=new points();
	String[] S=s.split(",");
	dp.setDistance(Double.parseDouble(S[1]));
	String[] names = S[0].split("&"); 
	n1.setN(names[0]);
	n2.setN(names[1]);
	for(points ps: Ps){
	 if(n1.getN().equals(ps.getN())){
	n1.setx(ps.getx());
	n1.sety(ps.gety());}
	else
	 if(n2.getN().equals(ps.getN())){
	 n2.setx(ps.getx());
	 n2.sety(ps.gety());}}

	dp.setPoint1(n1);
	dp.setPoint2(n2);
	pointd.add(dp);
	}
	
	 //start iterations
       boolean stop=false;
       while(!stop){
           
  //find the minimum distance
double mind= pointd.get(0).getDistance();
       pd m=pointd.get(0);
       for (pd B: pointd) {
       if(B.getDistance()<mind){
           mind=B.getDistance();
           m=B;} }
      pointd.remove(m);

	pd m2 = new pd();//m have A&B, m2 have B&A
	for(pd B2:pointd){
	if(B2.getPoint1().getN().equals(m.getPoint2().getN()) && B2.getPoint2().getN().equals(m.getPoint1().getN()))
	m2=B2;}
	pointd.remove(m2);

       //clustring part
       if(cl.size()==0){
	cluster c = new cluster();
           c.addm(m.getPoint1());
           c.addm(m.getPoint2());
           cl.add(c);
           exist.add(m.getPoint1());
           exist.add(m.getPoint2()); }
       else
           if(cl.size()>0){
	       cluster c = new cluster();
               boolean z=false;//true if both point already in any cluster
               boolean v=false;//true if both points already in same cluster
               boolean fs=false;//true if first point in cluster
               boolean sc=false;//true if second point in cluster
                
         //define z state
               for(points t:exist){
                   if(m.getPoint1().getN().equals(t.getN())){
                       for(points O:exist){
                           if(m.getPoint2().getN().equals(O.getN()))
        z=true;}}}
        
               if(!z){
               for(points t:exist){
                   if(m.getPoint1().getN().equals(t.getN())){
                 fs=true;}
                   if(m.getPoint2().getN().equals(t.getN())){
           sc=true;}}}
               
               //define v state
        if(z){
               for(cluster d: cl){
                   for(points v1:d.getPoint()){
                       if(m.getPoint1().getN().equals(v1.getN())){
                           for(points v2: d.getPoint()){
                               if(m.getPoint2().getN().equals(v2.getN())){
                                   v=true; }}}}
                   }}
               //to combine tow clusters
               if(z&&!v){//the points in clusters and not in same cluster.
	            cluster one=new cluster();
                    cluster two=new cluster();
                   for(cluster s: cl){
                       for(points pt: s.getPoint()){
                           if(m.getPoint1().getN().equals(pt.getN())){
                               one=s;}
                           else
                               if(m.getPoint2().getN().equals(pt.getN())){
                               two=s;}
                       }}
                   cl.remove(one);
                   cl.remove(two);
                   for(int g=0;g<two.getPoint().size();g++){
                       one.addm(two.getPoint().get(g));}
                   cl.add(one);}
               else  
  if(!z){
                   if(fs &&!sc){
                       for(cluster g: cl){
                           for(points y: g.getPoint()){
                               if (m.getPoint1().getN().equals(y.getN())){
                                   c=g;}}}
                           cl.remove(c);
                           c.addm(m.getPoint2());
                           exist.add(m.getPoint2());
                           cl.add(c);} 
                       else
                           if(!fs&&sc){
                              for(cluster g: cl){
                           for(points y: g.getPoint()){
                               if (m.getPoint2().getN().equals(y.getN())){
                                   c=g;}}}
                              cl.remove(c);
                           c.addm(m.getPoint1());
                           exist.add(m.getPoint1());
                           cl.add(c);}
                              else
                                if(!fs&&!sc){
                                    c.addm(m.getPoint1());
                                    c.addm(m.getPoint2());
                                    cl.add(c);
                                    exist.add(m.getPoint1());
                                    exist.add(m.getPoint2()); } 
               }}
           
       if(cl.get(cl.size()-1).getPoint().size()==Ps.size()){
           stop=true;}
        
      System.out.println("---------------------------------");
                       System.out.println("iteration = "+iteration++);
                       System.out.println("clusters are: ");
                          for(int k=0;k<cl.size();k++){
   System.out.println("cluster "+k+" "+cl.get(k).getPoint().toString());}
       }
       
    endTime = System.nanoTime();
         duration = (endTime - startTime)/1000000; 
          System.out.println("time = "+duration+" MS");
    }
	catch(IOException exception){exception.printStackTrace();}
	catch( InterruptedException e){}
	catch(ClassNotFoundException ex){}
        catch(Exception ex){System.out.println("File Not found");}    
    }}
