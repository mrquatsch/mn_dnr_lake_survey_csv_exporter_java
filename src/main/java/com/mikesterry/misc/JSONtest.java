package com.mikesterry.misc;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public class JSONtest {
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
//        Car car = new Car("Gray", "Escape");
//        try {
//            objectMapper.writeValue(new File("car.json"), car);
//        } catch(Exception e) {
//
//        }

        String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
        try {
            Car car2 = objectMapper.readValue(json, Car.class);
            System.out.println("Type: " + car2.getType());
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
//        Car car = objectMapper.readValue(new File("target/json_car.json"), Car.class);

        String json2 = "{ \"color\" : \"Black\", \"type\" : \"FIAT\" }";
        try {
            JsonNode jsonNode = objectMapper.readTree(json2);
            String color = jsonNode.get("color").toString();
            System.out.println("Color: " + color);
        } catch(Exception e) {

        }
    }
}
 class Car {
    private String color;
    private String type;

     public String getColor() {
         return color;
     }

     public void setColor(String color) {
         this.color = color;
     }

     public String getType() {
         return type;
     }

     public void setType(String type) {
         this.type = type;
     }
 }