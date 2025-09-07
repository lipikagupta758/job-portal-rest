package com.example.spring_boot_rest.repository;


import com.example.spring_boot_rest.model.JobPost;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<JobPost, Integer> {

    @Query("SELECT jp FROM JobPost jp WHERE jp.postProfile LIKE %?1% OR jp.postDesc LIKE %?2% ")
    List<JobPost> findByPostProfileOrPostDesc(String postProfile, String postDesc);
    //Can Use this method name for using DSL
    //    List<JobPost> findByPostProfileContainingOrPostDescContaining(String postProfile, String postDesc);

}

//
//List<JobPost> jobs= new ArrayList<>(Arrays.asList(
//        new JobPost(1, "Java Developer", "Experience in Java of 2 years", 2, List.of("Core Java", "J2EE", "Spring Boot", "Hibernate")),
//        new JobPost(2, "Frontend Developer", "Experience in JS for 3 years", 3, List.of("HTML", "CSS", "JavaScript", "React")),
//        new JobPost(3, "Data Scientist", "Experience in ML for 5 years", 5, List.of("Python", "Machine Learning", "Data Analysis")),
//        new JobPost(4, "Mobile App Developer", "Experience in Kotlin for 4+ years", 4, List.of("iOS Development", "Android Development", "Mobile App"))
//));
//
//public List<JobPost> getAllJobs(){
//    return jobs;
//}
//
//public void addJob(JobPost job){
//    jobs.add(job);
//}
//
//public JobPost getJob(int jobId) {
//    for(JobPost job :jobs) {
//        if (job.getPostId() == jobId)
//            return job;
//    }
//    return null;
//}
//
//public void updateJob(JobPost jobPost) {
//    for(JobPost job: jobs){
//        if(job.getPostId()== jobPost.getPostId()){
//            jobs.remove(job);
//            addJob(jobPost);
//            break;
//        }
//    }
//}
//
//public String deleteJob(int jobId) {
//    for(JobPost job: jobs){
//        if(job.getPostId()== jobId) {
//            jobs.remove(job);
//            return "Deleted";
//        }
//    }
//    return "Not Found";
//}