package com.example.matchsummary.dto;

public record MatchSummaryDTO(
 String date,String homeName,String homeImage,String awayName,String awayImage,
 String resultHome,String resultAway,String totalLocal,String firstLocal,String secondLocal,
 String thirdLocal,String fourthLocal,String extraLocal,String totalAway,String firstAway,
 String secondAway,String thirdAway,String fourthAway,String extraAway) {}
