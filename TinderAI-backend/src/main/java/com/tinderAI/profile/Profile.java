package com.tinderAI.profile;

public record Profile(
        String id,
        String firstName,
        String lastName,
        String bio,
        int age,
        String imageUrl,
        String ethnicity,
        Gender gender,
        String personalityType
) {
}
