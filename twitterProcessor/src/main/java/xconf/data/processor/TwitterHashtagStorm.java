package xconf.data.processor;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import xconf.data.processor.repository.MongoRepository;

import java.util.Arrays;

public class TwitterHashtagStorm {
    public static void main(String[] args) throws Exception {

        /*MongoRepository repository = new MongoRepository();
        repository.Save("Data");*/

        String consumerKey = args[0];
        String consumerSecret = args[1];
        String accessToken = args[2];
        String accessTokenSecret = args[3];

        String[] arguments = args.clone();
        String[] keywords = Arrays.copyOfRange(arguments, 4, arguments.length);

        Config config = new Config();
        config.setDebug(true);

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("twitter-spout", new TwitterSpout(consumerKey, consumerSecret, accessToken, accessTokenSecret, keywords));
        builder.setBolt("twitter-hashtag-reader-bolt", new HashtagBolt()).shuffleGrouping("twitter-spout");

        LocalCluster cluster = new LocalCluster();

        cluster.submitTopology("TwitterHashtagStorm", config, builder.createTopology());

    }
}
