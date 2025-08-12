package com.example.friendo.TrendingFeature.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.example.friendo.TrendingFeature.Model.TrendingModel;
import com.example.friendo.TrendingFeature.Repository.TrendingRepository;
//sd
import com.example.friendo.TrendingFeature.TrendingDTO.DTO;

import jakarta.annotation.PostConstruct;

@Service
public class TrendingService {
    private TrendingRepository trendingRepository;

    @Autowired
    public TrendingService(TrendingRepository trendingRepository){
        this.trendingRepository = trendingRepository;
    }
    private static String url = "https://serpapi.com/search?geo=PH&hl=en&engine=google_trends_trending_now&hours=24&api_key=";

    private static String key = "93f06654236604468e1e42c3ae016397c8a46b93b5647f80447a4043a62f3921";

    public List<DTO> getTreding(){
        try{
            String link = url + key;
            
            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int status = con.getResponseCode();

            if(status == HttpURLConnection.HTTP_OK){
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }

                JSONParser parser = new JSONParser();
                JSONObject object = (JSONObject) parser.parse(response.toString());
                JSONArray items = (JSONArray) object.get("trending_searches");

                int count = 0;

                List<DTO> dtos = new ArrayList<>();
                for(Object obj : items){
                    if(count >= 10){
                        break;
                    }
                    JSONObject search = (JSONObject)obj;
                    String title = (String)search.get("query");
                    Boolean active = (Boolean)search.get("active");
                    String Newslink = (String)search.get("serpapi_news_link");
                    Long volume = (Long)search.get("search_volume");
                    Long time = (Long) search.get("start_timestamp");
                    Date date = new Date(time * 1000);

                    SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd yyyy"); 
                    String formattedDate = sdf.format(date);

                    JSONArray obj2 = (JSONArray) search.get("categories");
                    JSONObject cat = (JSONObject) obj2.get(0);

                    String category = (String)cat.get("name");

                    System.out.println(title + " === " + active + " === " + volume + " === " + formattedDate + " === " + category + "=== " + Newslink);

                    dtos.add(new DTO(title,active,volume,formattedDate,category,Newslink));
                    count++;
                }
                br.close();
                // dtos.add(new DTO("chinese taipei vs philippines", true, 20000L, "August 06 2025", "Sports", "https://serpapi.com/search.json?engine=google_trends_news&page_token=1Wk7L3ica1xTlFpYmlpcEp-SWJI4ffK519fZHBal5i0K8Jh87n3EUU84O-5gPoz9ruzARBj7Tb38Mbi4q-gmuPr45RwQNgD5mDMN"));
                // dtos.add(new DTO("wednesday season 2", true, 10000L, "August 05 2025", "Entertainment", "https://serpapi.com/search.json?engine=google_trends_news&page_token=9IQPiHica1xTlFpYmlpcEp-SWJJ4h0Fo8rl3NfPvL0rNWxTgMfnc6xOCx2DsdzUHy2Ds96daA-Hs3U-yYOy39gtWwNWv7r8AV8OjOh3OXm9oDze_6LEqXL3vwrlw9mHxfLj6JZ05cHbx9W9w9uaXCLua1QVg7Bcv3A7D1Zy89BvCBgBAhH8O"));
                // dtos.add(new DTO("okx", true, 5000L, "August 05 2025", "Business and Finance", "https://serpapi.com/search.json?engine=google_trends_news&page_token=5HSWI3ica1xTlFpYmlpcEp-SWJI4dfK59_61FotS8xYFeEw-987ebD-M_T5ybi9cvNx4M1xcybsewgYAleMlwg"));
                // dtos.add(new DTO("visa bonds immigration", true, 5000L, "August 05 2025", "Law and Government", "https://serpapi.com/search.json?engine=google_trends_news&page_token=ZScWKXica1xTlFpYmlpcEp-SWJI4cfK59_HLXRel5i0KDQYAtp0NGw"));
                // dtos.add(new DTO("manny jacinto", true, 5000L, "August 05 2025", "Entertainment", "https://serpapi.com/search.json?engine=google_trends_news&page_token=-5ezvXica1xTlFpYmlpcEp-SWJI4c_K5dyt3_lyUmrcowAPIzttdDWO_3xF8D87e46gDY79OnRoAF1_kmwdnn25rgrHf7PsgBBevKLoEYQMAJB1Cvg"));
                // dtos.add(new DTO("gsis", true, 5000L, "August 05 2025", "Other", "https://serpapi.com/search.json?engine=google_trends_news&page_token=3UptdHica1xTlFpYmlpcEp-SWJI4efK595N3My5KzVsU4DH53Ds7oSgY-71_wiQIGwCKfRj5"));
                // dtos.add(new DTO("kelley mack", true, 2000L, "August 06 2025", "Entertainment", "https://serpapi.com/search.json?engine=google_trends_news&page_token=sj2eEnica1xTlFpYmlpcEp-SWJI4cfK595u2Cy1KzVsU4AEAuI4NOw"));
                // dtos.add(new DTO("chikungunya virus", true, 5000L, "August 05 2025", "Health", "https://serpapi.com/search.json?engine=google_trends_news&page_token=9HT5V3ica1xTlFpYmlpcEp-SWJI4efK510zLnBal5i0K8Jh87v1KMRMY-93Ut5oQNgCG0xlD"));
                // dtos.add(new DTO("jeremy renner accident", true, 2000L, "August 06 2025", "Entertainment", "https://serpapi.com/search.json?engine=google_trends_news&page_token=EWhH33ica1xTlFpYmlpcEp-SWJI4dfK518d_Zi9KzVsU4DH53PsVhUkw9tuUM3xw8arlu2DsNw5zr0LYAOpaKCg"));
                // dtos.add(new DTO("legionnaires disease", true, 2000L, "August 05 2025", "Health", "https://serpapi.com/search.json?engine=google_trends_news&page_token=cUNe0nica1xTlFpYmlpcEp-SWJI4cfK5t06PEhal5i0K8AAAuAQNQg"));
                return dtos;      
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }
    @Scheduled(initialDelay = 0,fixedRate = 12 * 60 * 60 * 1000)
    public void TrendingDayFetch(){

        List<DTO> dtos = getTreding();
        trendingRepository.deleteAll();
        for(DTO dto : dtos){
            TrendingModel trendingModel = TrendingModel.builder()
                .Newslink(dto.getNewslink())
                .active(true)
                .category(dto.getCategory())
                .data(dto.getData())
                .volume(dto.getVolume())
                .title(dto.getTitle())
                .build();
            trendingRepository.save(trendingModel);
        }      
    }
    public List<TrendingModel> getCurrentTrend(){
        return trendingRepository.findAll();
    }
}
