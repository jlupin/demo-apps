package com.example.currency.converter.controller;

import com.example.currency.converter.common.pojo.Currency;
import com.example.currency.converter.controller.in.ConvertIn;
import com.example.currency.converter.controller.out.ConvertOut;
import com.example.currency.converter.service.interfaces.CurrencyConverterChfService;
import com.example.currency.converter.service.interfaces.CurrencyConverterEurService;
import com.example.currency.converter.service.interfaces.CurrencyConverterGbpService;
import com.jlupin.impl.util.JLupinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Piotr Heilman
 */
@RestController
public class ConverterController {
    private final static Logger logger = LoggerFactory.getLogger(ConverterController.class);

    @Autowired
    @Qualifier("currencyConverterEurService")
    private CurrencyConverterEurService currencyConverterEURService;
    @Autowired
    @Qualifier("currencyConverterGbpService")
    private CurrencyConverterGbpService currencyConverterGBPService;
    @Autowired
    @Qualifier("currencyConverterChfService")
    private CurrencyConverterChfService currencyConverterCHFService;
    @Autowired
    private ServletContext servletContext;

    @GetMapping("/")
    public String index() {
        final String baseHref = servletContext.getContextPath();

        final String imagesStyles =
                "<style>" +
                "    .header-container .logo {\n" +
                "      background: url(\"" + baseHref + "/assets/images/jlupin_next_server_logo.png\") !important;\n" +
                "    }\n" +
                "    \n" +
                "    .architecture {\n" +
                "      background: url(\"" + baseHref + "/assets/images/demoapps.png\") !important;\n" +
                "    }\n" +
                "    .architecture .portal-access {\n" +
                "      background: url(\"" + baseHref + "/assets/images/demoapps_shadow_pa.png\") !important;\n" +
                "    }\n" +
                "    .architecture .main-server {\n" +
                "      background: url(\"" + baseHref + "/assets/images/demoapps_shadow_ms.png\") !important;\n" +
                "    }\n" +
                "    .architecture .currency-converter-eur {\n" +
                "      background: url(\"" + baseHref + "/assets/images/demoapps_shadow_eur.png\") !important;\n" +
                "    }\n" +
                "    .architecture .currency-converter-gbp {\n" +
                "      background: url(\"" + baseHref + "/assets/images/demoapps_shadow_gbp.png\") !important;\n" +
                "    }\n" +
                "    .architecture .currency-converter-chf {\n" +
                "      background: url(\"" + baseHref + "/assets/images/demoapps_shadow_chf.png\") !important;\n" +
                "    }\n" +
                "    .architecture .exchange-rates {\n" +
                "      background: url(\"" + baseHref + "/assets/images/demoapps_shadow_er.png\") !important;\n" +
                "    }\n" +
                "</style>";

        return "<!doctype html>\n" +
               "<html>\n" +
               "<head>\n" +
               "    <base href=\"" + baseHref + "\">\n" +
               "    <meta charset=\"utf-8\">\n" +
               "    <title>Currency converter</title>\n" +
               "\n" +
               "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
               "    <link rel=\"icon\" type=\"image/x-icon\" href=\"" + baseHref + "/favicon.ico\">\n" +
               "    <link rel=\"stylesheet\" href=\"" + baseHref + "/assets/external/bootstrap.min.css\">\n" +
               "\n" +
               imagesStyles + "\n" +
               "</head>\n" +
               "<body id=\"body\">\n" +
               "    <div class=\"container\">\n" +
               "        <app-root><p class=\"text-center\">Loading...</p></app-root>\n" +
               "    </div>\n" +
               "\n" +
               "    <script type=\"text/javascript\" src=\"" + baseHref + "/assets/external/jquery-3.1.1.slim.min.js\"></script>" +
               "    <script type=\"text/javascript\" src=\"" + baseHref + "/assets/external/tether.min.js\"></script>" +
               "    <script type=\"text/javascript\" src=\"" + baseHref + "/assets/external/bootstrap.min.js\"></script>" +
               "    <script type=\"text/javascript\" src=\"" + baseHref + "/inline.bundle.js\"></script>" +
               "    <script type=\"text/javascript\" src=\"" + baseHref + "/polyfills.bundle.js\"></script>" +
               "    <script type=\"text/javascript\" src=\"" + baseHref + "/styles.bundle.js\"></script>" +
               "    <script type=\"text/javascript\" src=\"" + baseHref + "/vendor.bundle.js\"></script>" +
               "    <script type=\"text/javascript\" src=\"" + baseHref + "/main.bundle.js\"></script>" +
               "</html>";
    }

    @CrossOrigin
    @PostMapping("/convert")
    public List<ConvertOut> convert(@RequestBody ConvertIn convertIn) {
        final List<ConvertOut> result = new ArrayList<>();

        for (final Currency currency : Currency.values()) {
            if (currency.equals(convertIn.getCurrency())) {
                continue;
            }

            final ConvertOut subResult = new ConvertOut();
            subResult.setCurrency(currency);
            BigDecimal convertedAmount = null;
            try {
                switch (currency) {
                    case EUR:
                        convertedAmount = currencyConverterEURService.convert(convertIn.getValue(), convertIn.getCurrency(), currency);
                        break;
                    case GBP:
                        convertedAmount = currencyConverterGBPService.convert(convertIn.getValue(), convertIn.getCurrency(), currency);
                        break;
                    case CHF:
                        convertedAmount = currencyConverterCHFService.convert(convertIn.getValue(), convertIn.getCurrency(), currency);
                        break;
                }
            } catch (Throwable th) {
                logger.error("an error occurred caused by:" + JLupinUtil.getHighestMessageFromThrowable(th), th);
            }
            subResult.setValue(convertedAmount);

            result.add(subResult);
        }

        return result;
    }
}
