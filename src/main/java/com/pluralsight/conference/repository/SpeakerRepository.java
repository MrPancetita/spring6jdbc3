package com.pluralsight.conference.repository;

import com.pluralsight.conference.model.Speaker;

import java.util.List;
/*Repository class to interact with DB*/

public interface SpeakerRepository {
    List<Speaker> findAll();

    Speaker create(Speaker speaker);
    Speaker getSpeaker(int id); 
    Speaker getLastSpeaker();

    Speaker updateSpeaker(Speaker speaker); 

}
