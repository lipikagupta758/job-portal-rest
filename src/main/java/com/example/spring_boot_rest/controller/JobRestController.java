package com.example.spring_boot_rest.controller;

import com.example.spring_boot_rest.model.JobPost;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.spring_boot_rest.service.JobService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

//@Controller  //by default, with controller, spring thinks that it will return a view name
@RestController  // either use @ResponseBody or use RestController
//@RequestMapping("/api")  -- common url part for all other urls can be defined here
@CrossOrigin(origins= "http://localhost:3000")   //This allow the spring project to connect with react project
public class JobRestController {

    @Autowired
    private JobService service;

//    By default, jackson library produces json format of java object. To produce, xml format, use jackson-xml library. and then accept thew request as application/xml in postman
//    Also, use produces parameter to produce xml data
//    @GetMapping(value = "jobPosts", produces = "application/xml")  //USing this will produce xml format only
    @GetMapping(value = "jobPosts")
    //@ResponseBody  //to convey that it returns json data and not a view name
    public ResponseEntity<List<JobPost>> getAllJobs(){
        return new ResponseEntity<>(service.getAllJobs(), HttpStatus.OK);
    }
//Through ResponseEntity, we can pass the HTTP Response Code with the object
    @GetMapping("jobPost/{jobId}")
    public ResponseEntity<JobPost> getJob(@PathVariable("jobId") int jobId){
        JobPost jobPost= service.getJob(jobId);
        if(jobPost.getPostId() >0)
            return new ResponseEntity<>(jobPost, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("jobPost/{jobId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int jobId){
        JobPost jobPost= service.getJob(jobId);
        if(jobPost.getPostId() >0)
            return new ResponseEntity<>(jobPost.getImageData(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @PostMapping(value = "jobPost")
//    public void postJob(@RequestBody JobPost jobPost){  //requestbody annotation helps the json data received from client to convert it into a java object
//        service.addJob(jobPost);
//    }

    @PostMapping(value= "/jobPost" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> postJob(@RequestPart("jobPost") String jobPostJson, @RequestPart("image") MultipartFile image){
        JobPost job= null;

        try {
            // Convert jobPostJson to JobPost object
            ObjectMapper mapper = new ObjectMapper();
            JobPost jobPost = mapper.readValue(jobPostJson, JobPost.class);
//          System.out.println("Received jobPostJson: " + jobPostJson);
//          System.out.println("Received image: " + (image != null ? image.getOriginalFilename() : "null"));

            job = service.addJob(jobPost, image);
            return new ResponseEntity<>(job, HttpStatus.CREATED);
        }
        catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @PutMapping(value = "jobPost", consumes = "application/xml")
    //Using consumes paramter, the method will only accept xml data.
    //If optionally, you want to use xml data, then accept as application/xml and add jackson-xml library.
    @PutMapping(value = "jobPost")
    public int updateJob(@RequestBody JobPost jobPost){
        service.updateJob(jobPost);
        return service.getJob(jobPost.getPostId()).getPostId();
    }

    @DeleteMapping("jobPost/{jobId}")
    public void /*String*/ deleteJob(@PathVariable("jobId") int jobId){
//        return service.deleteJob(jobId);
        service.deleteJob(jobId);
    }
//    @GetMapping("load")
//    public String loadData(){
//        service.loadData();
//        return "success";
//    }

    @GetMapping("jobPosts/keyword/{keyword}")
    public List<JobPost> searchByKeyword(@PathVariable("keyword") String keyword){
        return service.searchByKeyword(keyword);
    }

}