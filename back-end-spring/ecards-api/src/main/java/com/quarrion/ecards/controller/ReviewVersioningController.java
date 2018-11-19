package com.quarrion.ecards.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quarrion.ecards.model.Name;
import com.quarrion.ecards.model.ReviewV2;
import com.quarrion.ecards.model.ReviewRating;
import com.quarrion.ecards.model.ReviewV1;
import com.quarrion.ecards.model.ReviewV2;

@RestController
public class ReviewVersioningController {

	// URI VERSIONING:    http://localhost:8080/v1/review   or   http://localhost:8080/v2/review
  @GetMapping("v1/review")
  public ReviewV1 reviewV1() {
    return new ReviewV1(5);
  }
  @GetMapping("v2/review")
  public ReviewV2 reviewV2() {
    return new ReviewV2(ReviewRating.FIVE, "Best theme!");
  }

  
  // REQUEST PARAM VERSIONING:    http://localhost:8080/review/param?version=1  or    http://localhost:8080/review/param?version=2
  @GetMapping(value = "/review/param", params = "version=1")
  public ReviewV1 paramV1() {
    return new ReviewV1(5);
  }
  @GetMapping(value = "/review/param", params = "version=2")
  public ReviewV2 paramV2() {
    return new ReviewV2(ReviewRating.FIVE, "Best theme!");
  }

  
  
  // (Custom) Headers versioning:    http://localhost:8080/review/header   with   X-API-VERSION=1 or 2 (add this in the Headers tab in Postman)   
  @GetMapping(value = "/review/header", headers = "X-API-VERSION=1")
  public ReviewV1 headerV1() {
    return new ReviewV1(5);
  }
  @GetMapping(value = "/review/header", headers = "X-API-VERSION=2")
  public ReviewV2 headerV2() {
    return new ReviewV2(ReviewRating.FIVE, "Best theme!");
  }

  
  
  // Media type versioning (a.k.a “content negotiation” or “accept header”)
  // http://localhost:8080/review/produces with     Accept=application/vnd.company.app-v1+json  or v2   (add this in the Headers tab in Postman)  
  @GetMapping(value = "/review/produces", produces = "application/vnd.company.app-v1+json")
  public ReviewV1 producesV1() {
    return new ReviewV1(5);
  }
  @GetMapping(value = "/review/produces", produces = "application/vnd.company.app-v2+json")
  public ReviewV2 producesV2() {
    return new ReviewV2(ReviewRating.FIVE, "Best theme!");
  }

}