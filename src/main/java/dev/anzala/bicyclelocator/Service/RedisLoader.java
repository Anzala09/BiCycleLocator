package dev.anzala.bicyclelocator.Service;

import com.exasol.parquetio.data.Row;
import com.exasol.parquetio.reader.RowParquetReader;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.apache.hadoop.conf.Configuration;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.hadoop.util.HadoopInputFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import java.io.IOException;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.params.GeoAddParams;
import java.util.List;

@Setter
@Getter
@Service
public class RedisLoader {
    private final Jedis jedis ;

    @Autowired
    public RedisLoader(Jedis jedis) {
        this.jedis = jedis;
    }

    @PostConstruct
    public void loadData() {

      Path  path = new Path("/home/anzala/Downloads/BicycleLocatorParaquetFile/bicycle_data.parquet");

        Configuration conf = new Configuration();
        org.apache.hadoop.fs.Path hdfsPath = new org.apache.hadoop.fs.Path(path.toString());

        try (final ParquetReader<Row> reader = RowParquetReader.builder(HadoopInputFile.fromPath(hdfsPath, conf)).build()) {
            Row row = reader.read();
            while (row != null) {
                String bicycleId = (String) row.getValue("BicycleID");
                double latitude =(double) row.getValue("Latitude");
                double longitude = (double)row.getValue("Longitude");

                // jedis.geoadd("bicycles", longitude, latitude, bicycleId, GeoAddParams.geoAddParams().withHash());
                try{
                    jedis.geoadd(bicycleId, longitude, latitude, bicycleId);
                    //count1++;
                    //System.out.println(count1);
                }
                catch (Exception e){
                    // count++;
                    //System.out.println("error in adding locations"+count);
                }

                //System.out.println("Bicycle with id " + bicycleId + " added to Redis"+"response from redis" );
                row = reader.read();
            }
        }
        catch (Exception exception) {
            // Handle exception
            exception.printStackTrace();
        }
    }
}
