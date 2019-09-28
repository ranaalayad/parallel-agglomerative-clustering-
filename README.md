# parallel-agglomerative-clustering-
The code in ubuntu, it take a point (name, x coordinate, y coordinate) as an input, the output will be the number of iteration, clusters with the points inside, from the first cluster until being one cluster in addition to the time to end this process.

(to run my code you just need agglomerative.jar and input.txt )
at first HAC file must be on Desktop and contain agglomerative.jar, agglomerative.java and input file with name input.txt
to make file directory:
1-Hadoop fs mkdir /HAC
2- Hadoop fs mkdir /HAC/input
3-hadoop fs -put input.txt  /HAC/input
when the code is agglomerative.jar go to directory, in my case it is:
1-cd Desktop/HAC
2-hadoop jar agglomerative.jar /HAC/input-file-name-in-HDFS /HAC/output
Then the output will show in the screen.

	Addition hints:
o	how to convert agglomerative.java to agglomerative.jar :
http://tuttlem.github.io/2014/01/30/create-a-mapreduce-job-using-java-and-maven.html
 when convert code.java to code.jar make sure the files( points.java, pd.java, cluster.java and agglomerative.java) in same folder.
o	when you use new input file please keep it in the name (input.txt) and put it in the same folder with code.java and code.jar files. 
o	put this new input file in HDFS.
