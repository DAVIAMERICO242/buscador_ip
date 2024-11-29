package org.example;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.Country;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException, GeoIp2Exception, URISyntaxException {
        File database = new File(Main.class.getClassLoader().getResource("GeoLite2-City.mmdb").getFile());
        DatabaseReader reader = new DatabaseReader.Builder(database).build();
        InetAddress ipAddress = InetAddress.getByName("2804:2190:d40f:4d0:b90d:f2c0:6988:90ec");
        CityResponse response = reader.city(ipAddress);
        System.out.println(response);
        System.out.println(response.getCity().getName());
        System.out.println(response.getSubdivisions().get(0).getIsoCode());
    }
}