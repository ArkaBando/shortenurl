package com.shortenurl.controller;

import com.shortenurl.core.common.UrlUtil;
import com.shortenurl.core.dto.FullUrl;
import com.shortenurl.core.dto.ShortUrl;
import com.shortenurl.core.error.InvalidUrlError;
import com.shortenurl.core.service.UrlService;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.MalformedURLException;

@Controller
public class IndexController {

    public static final String HTTPS_SHORTEN_IO = "https://shorten.io";
    private final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private UrlService urlService;

    @GetMapping("/")
    public String index(ModelMap model){
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, params = "convert")
    public String convert(ModelMap model, @RequestParam String url){
        model.put("url",url);
        try{
            model.put("shorturl",convertFullUrlToShort(new FullUrl(url)));
        } catch (Exception e){
            model.put("errorMessage",e.getMessage());
        }

        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, params = "invert")
    public String invert(ModelMap model, @RequestParam String url){
        model.put("url",url);
        try{
            model.put("fullurl",urlService.getFullUrl(url).getFullUrl());
        } catch (Exception e){
            model.put("errorMessage",e.getMessage());
        }

        return "index";
    }

    public String convertFullUrlToShort(FullUrl fullUrl){
        // Validation checks to determine if the supplied URL is valid
        UrlValidator validator = new UrlValidator(
                new String[]{"http", "https"}
        );
        String url = fullUrl.getFullUrl();
        if (!validator.isValid(url)) {
            logger.error("Malformed Url provided");

            InvalidUrlError error = new InvalidUrlError("url", fullUrl.getFullUrl(), "Invalid URL");

            // returns a custom body with error message and bad request status code
            throw new RuntimeException("Invalid Url"+error.getMessage());
        }
        String baseUrl = null;

        try {
            baseUrl = UrlUtil.getBaseUrl(HTTPS_SHORTEN_IO);
        } catch (MalformedURLException e) {
            logger.error("Malformed request url");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request url is invalid", e);
        }

        // Retrieving the Shortened url and concatenating with protocol://domain:port
        ShortUrl shortUrl = urlService.getShortUrl(fullUrl);
        shortUrl.setShortUrl(baseUrl + shortUrl.getShortUrl());

        logger.debug(String.format("ShortUrl for FullUrl %s is %s", fullUrl.getFullUrl(), shortUrl.getShortUrl()));

        return shortUrl.getShortUrl();
    }
}
