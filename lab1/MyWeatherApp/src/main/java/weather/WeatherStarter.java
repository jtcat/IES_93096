package weather;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import weather.ipma_client.IpmaCityForecast;
import weather.ipma_client.IpmaService;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.Arrays;

/**s
 * demonstrates the use of the IPMA API for weather forecast
 */
public class WeatherStarter {

    //todo: should generalize for a city passed as argument
    //private static int CITY_ID = 1010500;

    private static final Logger logger = LogManager.getLogger(WeatherStarter.class);

    public static void  main(String[] args ) {

        if (args.length == 0) {
            logger.warn("City ID argument missing");
            System.exit(1) ;
        }
        int CITY_ID = Integer.parseInt(args[0]);
        // get a retrofit instance, loaded with the GSon lib to convert JSON into objects
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.ipma.pt/open-data/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // create a typed interface to use the remote API (a client)
        IpmaService service = retrofit.create(IpmaService.class);
        // prepare the call to remote endpoint
        Call<IpmaCityForecast> callSync = service.getForecastForACity(CITY_ID);
        logger.debug("Forecast request logged for ID:" + CITY_ID);
        try {
            Response<IpmaCityForecast> apiResponse = callSync.execute();
            IpmaCityForecast forecast = apiResponse.body();

            if (forecast != null) {
                var firstDay = forecast.getData().listIterator().next();

                logger.debug(String.format("Response logged: max temp for %s is %4.1f %n",
                        firstDay.getForecastDate(),
                        Double.parseDouble(firstDay.getTMax())));
            } else {
                logger.debug( "Response logged: No results for this request!");
            }
        } catch (Exception ex) {
            logger.trace("Trace logged:" + Arrays.toString(ex.getStackTrace()));
        }

    }
}