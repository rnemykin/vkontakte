package com.abudko.reseller.huuto.query.webclient.city;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebWindow;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

@Component
public class CityWebClient {
    
    private Logger log = LoggerFactory.getLogger(getClass());

    private WebClient webClient = new WebClient(BrowserVersion.CHROME);

    @PostConstruct
    public void init() {
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setTimeout(10000000);
    }
    
    public String call(String url) {
        String html = null;
        try {
            HtmlPage page = webClient.getPage(url);
            Thread.sleep(2000);
            webClient.waitForBackgroundJavaScript(30000);
            html = page.asXml();
        } catch (InterruptedException | FailingHttpStatusCodeException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
            close();
        }
        
        return html;
    }
    
    protected void close() {
        try {
            List<WebWindow> windows = webClient.getWebWindows();
            for (WebWindow ww : windows) {
                ww.getJobManager().removeAllJobs();
                ww.getJobManager().shutdown();
            }
            webClient.close();
            System.gc();
        } catch (Exception e) {
            log.warn("Closing webclient: " + e);
            return;
        }
    }
}