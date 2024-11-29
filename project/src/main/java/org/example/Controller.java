package org.example;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/look_ip")
public class Controller {

    private DatabaseReader reader = new DatabaseReader.Builder(Main.class.getClassLoader().getResourceAsStream("GeoLite2-City.mmdb")).build();

    public Controller() throws IOException {

    }

    @GetMapping
    public ResponseEntity getLocation(@RequestParam(required = false) String ip, Boolean from_me, HttpServletRequest request) throws IOException, GeoIp2Exception {
        try{
            String used_ip = ip;
            if(from_me!=null && from_me){//não funciona localmente, apenas em produção
                if(request.getHeader("X-FORWARDED-FOR")!=null && !request.getHeader("X-FORWARDED-FOR").isBlank()){
                    used_ip = request.getHeader("X-FORWARDED-FOR");
                }else{
                    used_ip = request.getRemoteAddr();
                }
            }
            InetAddress ipAddress = InetAddress.getByName(used_ip);//ipv4 e ipv6
            CityResponse response = this.reader.city(ipAddress);
            System.out.println(response);
            System.out.println(response.getCity().getName());
            System.out.println(response.getSubdivisions().get(0).getIsoCode());
            return ResponseEntity.ok().body(
                    new LocationResponse(
                            response.getSubdivisions().get(0).getIsoCode(),
                            response.getCity().getName(),
                            response.getLocation().getLatitude(),
                            response.getLocation().getLongitude())
            );
        }catch (Exception e){
            return ResponseEntity.status(500).body(e.getLocalizedMessage());
        }

    }
}
