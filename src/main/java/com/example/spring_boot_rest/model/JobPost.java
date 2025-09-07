package com.example.spring_boot_rest.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
//@AllArgsConstructor
@Component
@Entity
public class JobPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   //This will automatically create the postId
    private int postId;
    private String postProfile;
    private String postDesc;
    private int reqExperience;
    private List<String> postTechStack;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")   //This helps to change the format of date visible on front-end
    private Date jobPostDate;

    private String imageType;
    @Lob  //LargeObject
    private byte[] imageData;

    public JobPost(int postId, String postProfile, String postDesc, int reqExperience, List<String> postTechStack, Date jobPostDate, String imageType, byte[] imageData) {
        this.postId = postId;
        this.postProfile = postProfile;
        this.postDesc = postDesc;
        this.reqExperience = reqExperience;
        this.postTechStack = postTechStack;
        this.jobPostDate = jobPostDate;
        this.imageType = imageType;
        this.imageData = imageData;
    }

    public JobPost(int postId){
        this.postId= postId;
    }
}
