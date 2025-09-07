package com.example.spring_boot_rest.service;

import com.example.spring_boot_rest.model.JobPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.spring_boot_rest.repository.JobRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class JobService {
    //This is called DTO (Data Transfer Objects)- because we are sending and receiving data between different layers
    @Autowired
    public JobRepository repository;

//    public void addJob(JobPost job){
////        repository.addJob(job);
//        repository.save(job);
//    }

    public JobPost addJob(JobPost jobPost, MultipartFile image) throws IOException {
        jobPost.setImageType(image.getContentType());
        jobPost.setImageData(image.getBytes());

        return repository.save(jobPost);
    }
    public List<JobPost> getAllJobs(){
//        return repository.getAllJobs();
        return repository.findAll();
    }

    public JobPost getJob(int jobId) {
//        return repository.getJob(jobId);
        return repository.findById(jobId).orElse(new JobPost(-1));
    }

    public void updateJob(JobPost jobPost) {
//        repository.updateJob(jobPost);
        repository.save(jobPost);
    }

    public  void /*String*/ deleteJob(int jobId) {
//        return repository.deleteJob(jobId);
        repository.deleteById(jobId);
    }

//    public void loadData() {
//        List<JobPost> jobs= new ArrayList<>(Arrays.asList(
//        new JobPost(1, "Java Developer", "Experience in Java of 2 years", 2, List.of("Core Java", "J2EE", "Spring Boot", "Hibernate")),
//        new JobPost(2, "Frontend Developer", "Experience in JS for 3 years", 3, List.of("HTML", "CSS", "JavaScript", "React")),
//        new JobPost(3, "Data Scientist", "Experience in ML for 5 years", 5, List.of("Python", "Machine Learning", "Data Analysis")),
//        new JobPost(4, "Mobile App Developer", "Experience in Kotlin for 4+ years", 4, List.of("iOS Development", "Android Development", "Mobile App"))
//));
//        repository.saveAll(jobs);
//    }

    public List<JobPost> searchByKeyword(String keyword) {
        return repository.findByPostProfileOrPostDesc(keyword, keyword);
    }
}
