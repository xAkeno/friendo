package com.example.friendo.TrendingFeature.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

//sd
import com.example.friendo.TrendingFeature.TrendingDTO.DTO;

@Service
public class TrendingService {
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
                // BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                // StringBuilder response = new StringBuilder();
                // String inputLine;
                // while ((inputLine = br.readLine()) != null) {
                //     response.append(inputLine);
                // }

                // JSONParser parser = new JSONParser();
                // JSONObject object = (JSONObject) parser.parse(response.toString());
                // JSONArray items = (JSONArray) object.get("trending_searches");

                // int count = 0;

                List<DTO> dtos = new ArrayList<>();
                // for(Object obj : items){
                //     if(count >= 10){
                //         break;
                //     }
                //     JSONObject search = (JSONObject)obj;
                //     String title = (String)search.get("query");
                //     Boolean active = (Boolean)search.get("active");
                //     Long volume = (Long)search.get("search_volume");
                //     Long time = (Long) search.get("start_timestamp");
                //     Date date = new Date(time * 1000);

                //     SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd yyyy"); 
                //     String formattedDate = sdf.format(date);

                //     JSONArray obj2 = (JSONArray) search.get("categories");
                //     JSONObject cat = (JSONObject) obj2.get(0);

                //     String category = (String)cat.get("name");

                //     System.out.println(title + " === " + active + " === " + volume + " === " + formattedDate + " === " + category);

                //     dtos.add(new DTO(title,active,volume,formattedDate,category));
                //     count++;
                // }
                // br.close();
                dtos.add(new DTO("timberwolves vs lakers", true, 20000L, "April 26 2025", "Sports"));
                dtos.add(new DTO("magic vs celtics", true, 10000L, "April 26 2025", "Sports"));
                dtos.add(new DTO("bucks vs pacers", true, 10000L, "April 26 2025", "Sports"));
                dtos.add(new DTO("gary trent jr.", true, 10000L, "April 26 2025", "Sports"));
                dtos.add(new DTO("lakers vs wolves game 3", true, 5000L, "April 26 2025", "Sports"));
                dtos.add(new DTO("ginebra vs san miguel", true, 10000L, "April 25 2025", "Sports"));
                dtos.add(new DTO("sb19 dungka", true, 2000L, "April 25 2025", "Entertainment"));
                dtos.add(new DTO("genshin codes", true, 2000L, "April 25 2025", "Games"));
                dtos.add(new DTO("kang ji yong dead", true, 2000L, "April 25 2025", "Other"));
                dtos.add(new DTO("philippines", true, 5000L, "April 25 2025", "Law and Government"));
                return dtos;      
            }
        }catch(Exception e){
            System.out.println(e);
        }

        return null;
    }
}
