/************************************************************************************
* Busca CEP 1.0
* c0d3r: Danilo Vaz
* Dt: 29/10/2013
* E-mail: danilovazb[at]gmail[dot]com
* 
* ~~> Código que faz parse XML no webservice do qualocep.com.br, 
* ~~> fiz uma integração também com o Google Maps para trazer o geocode do CEP
* assim é possível implementá-lo em uma aplicação com mapas.
* 
************************************************************************************/
 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketTimeoutException;
 
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
 
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 
public class buscaCEP {
 
    public String getEndereco(String CEP) throws IOException {
 
        // ***************************************************
        try {
 
            Document doc = Jsoup
                    .connect("http://www.qualocep.com/busca-cep/" + CEP)
                    .timeout(120000).get();
            Elements urlPesquisa = doc.select("span[itemprop=streetAddress]");
            for (Element urlEndereco : urlPesquisa) {
                return urlEndereco.text();
            }
 
        } catch (SocketTimeoutException e) {
 
        } catch (HttpStatusException w) {
 
        }
        return CEP;
    }
 
    public String getBairro(String CEP) throws IOException {
 
        // ***************************************************
        try {
 
            Document doc = Jsoup
                    .connect("http://www.qualocep.com/busca-cep/" + CEP)
                    .timeout(120000).get();
            Elements urlPesquisa = doc.select("td:gt(1)");
            for (Element urlBairro : urlPesquisa) {
                return urlBairro.text();
            }
 
        } catch (SocketTimeoutException e) {
 
        } catch (HttpStatusException w) {
 
        }
        return CEP;
    }
 
    public String getCidade(String CEP) throws IOException {
 
        // ***************************************************
        try {
 
            Document doc = Jsoup
                    .connect("http://www.qualocep.com/busca-cep/" + CEP)
                    .timeout(120000).get();
            Elements urlPesquisa = doc.select("span[itemprop=addressLocality]");
            for (Element urlCidade : urlPesquisa) {
                return urlCidade.text();
            }
 
        } catch (SocketTimeoutException e) {
 
        } catch (HttpStatusException w) {
 
        }
        return CEP;
    }
 
    public String getUF(String CEP) throws IOException {
 
        // ***************************************************
        try {
 
            Document doc = Jsoup
                    .connect("http://www.qualocep.com/busca-cep/" + CEP)
                    .timeout(120000).get();
            Elements urlPesquisa = doc.select("span[itemprop=addressRegion]");
            for (Element urlUF : urlPesquisa) {
                return urlUF.text();
            }
 
        } catch (SocketTimeoutException e) {
 
        } catch (HttpStatusException w) {
 
        }
        return CEP;
    }
 
    public String getLatLong(String CEP) throws IOException, ParseException {
 
        // ***************************************************
        try {
 
            if (CEP.contains("-")) {
                Document doc = Jsoup
                        .connect(
                                "http://maps.googleapis.com/maps/api/geocode/xml?address="
                                        + CEP + "&language=pt-BR&sensor=true")
                        .timeout(120000).get();
                Elements lat = doc.select("geometry").select("location")
                        .select("lat");
                Elements lng = doc.select("geometry").select("location")
                        .select("lng");
                for (Element Latitude : lat) {
                    for (Element Longitude : lng) {
                        return Latitude.text() + "," + Longitude.text();
                    }
 
                }
            } else {
 
                StringBuilder cepHif = new StringBuilder(CEP);  
                cepHif.insert(CEP.length() - 3, '-');
 
                Document doc = Jsoup
                        .connect(
                                "http://maps.googleapis.com/maps/api/geocode/xml?address="
                                        + cepHif + "&language=pt-BR&sensor=true")
                        .timeout(120000).get();
                Elements lat = doc.select("geometry").select("location")
                        .select("lat");
                Elements lng = doc.select("geometry").select("location")
                        .select("lng");
                for (Element Latitude : lat) {
                    for (Element Longitude : lng) {
                        return Latitude.text() + "," + Longitude.text();
                    }
 
                }
            }
 
        } catch (SocketTimeoutException e) {
 
        } catch (HttpStatusException w) {
 
        }
        return CEP;
    }
 
}
