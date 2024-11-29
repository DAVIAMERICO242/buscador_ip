package org.example;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/look_ip")
public class Controller {
    @GetMapping
    public ResponseEntity getLocation(@RequestParam String ip) throws IOException, GeoIp2Exception {
        File database = new File(Main.class.getClassLoader().getResource("GeoLite2-City.mmdb").getFile());
        DatabaseReader reader = new DatabaseReader.Builder(database).build();
        InetAddress ipAddress = InetAddress.getByName(ip);//ipv4 e ipv6
        CityResponse response = reader.city(ipAddress);
        System.out.println(response);
        System.out.println(response.getCity().getName());
        System.out.println(response.getSubdivisions().get(0).getIsoCode());
        return ResponseEntity.ok().body(new LocationResponse(response.getSubdivisions().get(0).getIsoCode(),response.getCity().getName()));
    }
}
