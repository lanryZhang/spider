package com.ifeng.pgc.core;

import com.ifeng.pgc.core.monitor.spider.SpiderMonitor;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.PlainText;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;

/**
 * Created by zhanglr on 2016/7/6.
 */
public class PhantomJsDownloader implements Downloader, Closeable {
    private Logger log = Logger.getLogger(SpiderMonitor.class);
    private String driverPath = "phantomjs";
    private String driverName = "phantomjs.binary.path";
    private int waitDownloadTime;
    private int poolSize = 1;
    private Logger logger = Logger.getLogger(PhantomJsDownloader.class);
    private volatile WebDriverPool webDriverPool;
    private int pageHeight = 3000;
    private int pageWidth = 1000;
    Capabilities c = new Capabilities() {
        @Override
        public String getBrowserName() {
            return null;
        }

        @Override
        public Platform getPlatform() {
            return null;
        }

        @Override
        public String getVersion() {
            return null;
        }

        @Override
        public boolean isJavascriptEnabled() {
            return false;
        }

        @Override
        public Map<String, ?> asMap() {
            return null;
        }

        @Override
        public Object getCapability(String s) {
            return null;
        }

        @Override
        public boolean is(String s) {
            return false;
        }
    };

    public PhantomJsDownloader() {

    }

    public PhantomJsDownloader(String driverPath) {
        this.driverPath = driverPath;
        System.getProperties().setProperty(driverName, driverPath);
    }

    @Override
    public Page download(Request request, Task task) {
        WebDriver webDriver = null;
        try {
            checkInit();
            try {
                webDriver = webDriverPool.get();
            } catch (InterruptedException e) {
                logger.warn("interrupted", e);
                return null;
            }
            logger.info("downloading page " + request.getUrl());
            webDriver.manage().window().setSize(new Dimension(pageWidth, pageHeight));
            webDriver.get(request.getUrl());
            try {
                Thread.sleep(waitDownloadTime);
            } catch (InterruptedException e) {
                log.error(e);
            }
            WebDriver.Options manage = webDriver.manage();

            Site site = task.getSite();
            if (site.getCookies() != null) {
                for (Map.Entry<String, String> cookieEntry : site.getCookies()
                        .entrySet()) {
                    Cookie cookie = new Cookie(cookieEntry.getKey(),
                            cookieEntry.getValue());
                    manage.addCookie(cookie);
                }
            }
        /*
		 * TODO You can add mouse event or other processes
		 *
		 */
            WebElement webElement = webDriver.findElement(By.xpath("/html"));

            String content = webElement.getAttribute("outerHTML");
            Page page = new Page();
            page.setRawText(content);
            page.setUrl(new PlainText(request.getUrl()));
            page.setRequest(request);


//            webDriver.findElement(By.cssSelector("class=\"a1\"")).click();
            webDriverPool.returnToPool(webDriver);
            return page;
        } catch (Exception e) {
            if (webDriver != null) {
                webDriverPool.returnToBrokenPool(webDriver);
            }
        }
        return null;
    }


    private void checkInit() {
        if (webDriverPool == null) {
            synchronized (this) {
                webDriverPool = new WebDriverPool(poolSize);
            }
        }
    }

    @Override
    public void setThread(int thread) {
        this.poolSize = thread;
    }

    @Override
    public void close() throws IOException {
        webDriverPool.closeAll();
    }

    public void setPageHeight(int pageHeight) {
        this.pageHeight = pageHeight;
    }

    public void setPageWidth(int pageWidth) {
        this.pageWidth = pageWidth;
    }

    public void setDriverPath(String driverPath) {
        this.driverPath = driverPath;
    }

    public void setWaitDownloadTime(int waitDownloadTime) {
        this.waitDownloadTime = waitDownloadTime;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
}
