package org.tn.subscriptiontool.core;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class RestfulController {


    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/stripe-ids")
    public Map<String, String> getStripeIds(){
        Map<String,String> stripeIds = new HashMap<>();
        stripeIds.put("product1", "test_00g5nK4pKaD85DWbIJ");
        stripeIds.put("product2", "test_00g3fCbSceTo3vOfZ0");
        stripeIds.put("product3", "yet_another_stripe_id_here");
        return stripeIds;
    }
}
