package com.pluralsight.conference.service;

import com.pluralsight.conference.model.Speaker;

import java.util.List;
/*Service class for business logic */

public interface SpeakerService {
    List<Speaker> findAll();

    Speaker create(Speaker speaker);
    Speaker getSpeaker(int id);
    Speaker getLastSpeaker();

    Speaker updateSpeaker(Speaker speaker);

    void batch();

    void delete(int id);
}
