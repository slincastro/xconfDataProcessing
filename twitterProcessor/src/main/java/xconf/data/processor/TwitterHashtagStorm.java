package xconf.data.processor;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.mongodb.bolt.MongoInsertBolt;
import org.apache.storm.mongodb.common.mapper.MongoMapper;
import org.apache.storm.topology.TopologyBuilder;

import java.util.Arrays;

public class TwitterHashtagStorm {
    public static void main(String[] args) throws Exception {

        String consumerKey = args[0];
        String consumerSecret = args[1];
        String accessToken = args[2];
        String accessTokenSecret = args[3];

        String[] arguments = args.clone();
        String[] keywords = Arrays.copyOfRange(arguments, 4, arguments.length);

        Config config = new Config();
        config.setDebug(true);

        String url = "mongodb://127.0.0.1:27017/test";
        String collectionName = "tweets";

        MongoMapper mapper = new SimpleMongoMapper()
                .withFields("hashtags");

        MongoInsertBolt insertBolt = new MongoInsertBolt(url, collectionName, mapper);


        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("twitter-spout", new TwitterSpout(consumerKey, consumerSecret, accessToken, accessTokenSecret, keywords));
        builder.setBolt("twitter-hashtag-reader-bolt", new HashtagBolt()).shuffleGrouping("twitter-spout");
        builder.setBolt("insert", insertBolt, 1).shuffleGrouping("twitter-hashtag-reader-bolt");
        LocalCluster cluster = new LocalCluster();

        cluster.submitTopology("TwitterHashtagStorm", config, builder.createTopology());

    }
}
