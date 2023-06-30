package com.webingate.GroceryOrdering.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@CrossOrigin
public class buyercontroller {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${database.name}")
    private String schema;

    @GetMapping("GetBuyersList")
    @ResponseBody
    public List<buyer> getBuyersList() {
        List<buyer> buyerList = new ArrayList<>();

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(schema)
                
                
                .withProcedureName("GetBuyersList");

        try {
            Map<String, Object> resultData = simpleJdbcCall.execute();

            for (Map.Entry<String, Object> entry : resultData.entrySet()) {
                if (entry.getValue() instanceof List) {
                    List<Object> objList = (List<Object>) entry.getValue();
                    for (Object obj : objList) {
                        if (obj instanceof Map) {
                            Map<String, Object> map = (Map<String, Object>) obj;
                            buyer buyer = new buyer();
                            buyer.setId((Integer) map.get("Id"));
                            buyerList.add(buyer);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return buyerList;
    }

    @PostMapping("savebuyersList")
    @ResponseBody
    public List<buyer> saveBuyersList(@RequestBody buyer buyer) {
        List<buyer> buyersList = new ArrayList<>();

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(schema)
                .withProcedureName("insupddelbuyer");

        Map<String, Object> inParamMap = new HashMap<>();
        inParamMap.put("i_id", buyer.getId());
        inParamMap.put("i_name", buyer.getName());
        inParamMap.put("i_flag", buyer.getFlag());
        inParamMap.put("i_mobileno", buyer.getMobileno());
        inParamMap.put("i_total", buyer.getTotal());

        try {
            Map<String, Object> resultData = simpleJdbcCall.execute(inParamMap);

            for (Map.Entry<String, Object> entry : resultData.entrySet()) {
                if (entry.getValue() instanceof List) {
                    List<Object> objList = (List<Object>) entry.getValue();
                    for (Object obj : objList) {
                        if (obj instanceof Map) {
                            Map<String, Object> map = (Map<String, Object>) obj;
                            buyer b1 = new buyer();
                            b1.setId((Integer) map.get("Id"));
                            b1.setName((String) map.get("name"));
                            b1.setMobileno((String) map.get("mobileno"));
                            b1.setTotal((String) map.get("total"));
                            buyersList.add(b1);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return buyersList;
    }

    
   
    @GetMapping("verifylogin")
    @ResponseBody
    public buyer verifylogin(String username) {
        buyer buyers = new buyer();

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(schema)
                .withProcedureName("VerifyLogin");

        Map<String, Object> inParamMap = new HashMap<>();
        inParamMap.put("p_name", username);

        try {
            Map<String, Object> resultData = simpleJdbcCall.execute(inParamMap);

            for (Map.Entry<String, Object> entry : resultData.entrySet()) {
                if (entry.getValue() instanceof List) {
                    List<Object> objList = (List<Object>) entry.getValue();
                    for (Object obj : objList) {
                        if (obj instanceof Map) {
                            Map<String, Object> map = (Map<String, Object>) obj;
                            
                            buyers.setId((Integer) map.get("Id"));
                            buyers.setName((String) map.get("name"));
                            
                            
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return buyers;
    }
    
    
    
    @PostMapping("createbuyer")
    @ResponseBody
    public ResponseEntity<String> createBuyer(@RequestBody buyer newBuyer) {
        if (newBuyer == null) {
            return ResponseEntity.badRequest().body("Invalid buyer data.");
        }

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(schema)
                .withProcedureName("CreateBuyer");

        Map<String, Object> inParamMap = new HashMap<>();
        inParamMap.put("p_name", newBuyer.getName());
        inParamMap.put("p_mobileno", newBuyer.getMobileno());

        try {
            Map<String, Object> resultData = simpleJdbcCall.execute(inParamMap);

            for (Map.Entry<String, Object> entry : resultData.entrySet()) {
                if (entry.getValue() instanceof List) {
                    List<Object> objList = (List<Object>) entry.getValue();
                    for (Object obj : objList) {
                        if (obj instanceof Map) {
                            Map<String, Object> map = (Map<String, Object>) obj;

                            newBuyer.setName((String) map.get("name"));
                            newBuyer.setMobileno((String) map.get("mobileno"));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
          
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        String jsonResponse = "{\"message\": \"Buyer created successfully\"}";
        return ResponseEntity.ok(jsonResponse);
    }

    
    
    
    
    
    
    public static class buyer {
        private int id;
        private String name;
        private String mobileno;
        private String total;
        private String flag;

        public int getId() {
            return id;
        }

        public static void setname(String string) {
			// TODO Auto-generated method stub
			
		}

		public static void setmobilemo(String string) {
			// TODO Auto-generated method stub
			
		}

		public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobileno() {
            return mobileno;
        }

        public void setMobileno(String mobileno) {
            this.mobileno = mobileno;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }
    }
}
