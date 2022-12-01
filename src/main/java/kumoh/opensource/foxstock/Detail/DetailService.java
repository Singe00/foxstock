package kumoh.opensource.foxstock.Detail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class DetailService {
    Detail detail = new Detail();

    public Detail returnDetail(String name,String srtn_cd) {
        String URL1;
        String URL2;
        Document doc1;
        Document doc2;


        URL1 = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=" + name;
        URL2 = "https://finance.naver.com/item/main.nhn?code=" + srtn_cd;


        try {
            doc1 = Jsoup.connect(URL1).get();
            doc2 = Jsoup.connect(URL2).get();

            Elements chart = doc1.select("#_cs_root > div.ar_cont > div.cont_grp > div.grp_img > div:nth-child(6) > a > img");
            Elements type = doc2.select("#content > div.section.trade_compare > h4 > em > a");
            Elements info = doc2.select("#summary_info");


            detail.setChart(chart.get(0).attr("abs:src"));
            detail.setType(type.text());
            detail.setInfo(info.text());

            log.info("\n{}\n=======================\n {}\n=======================\n {}",detail.getChart(),detail.getType(),detail.getInfo());

            return detail;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IllegalStateException("오류가 발생하였습니다.");
        }
    }
}
