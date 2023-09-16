package com.pluralsight.conference.service;

import com.pluralsight.conference.model.Speaker;
import com.pluralsight.conference.repository.SpeakerRepository;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

//ServiceTier for business logic

@Service("speakerService")
public class SpeakerServiceImpl implements SpeakerService {

    private SpeakerRepository speakerRepository;

    public SpeakerServiceImpl(SpeakerRepository speakerRepository) {
        this.speakerRepository = speakerRepository;
    }

    @Override
    public List<Speaker> findAll() {
        return speakerRepository.findAll();
    }

    @Override
    public Speaker create(Speaker speaker) {
        return speakerRepository.create(speaker);
    }

    @Override
    public Speaker getSpeaker(int id) {
        return speakerRepository.getSpeaker(id);
    }
    
    @Override
    public Speaker getLastSpeaker() {
        return speakerRepository.getLastSpeaker();
    }

    @Override
    public Speaker updateSpeaker(Speaker speaker) {
        return speakerRepository.updateSpeaker(speaker);
    }

    @Override
    @Transactional
    public void batch() {
        List<Speaker> speakers = speakerRepository.findAll();
        
        List<Object[]> pairs = new ArrayList<>();
        for (Speaker speaker : speakers) {
            Object[] tmp = {"Java", speaker.getId()};
            pairs.add(tmp);
        }

        speakerRepository.updateSpeaker(pairs);

        //throw new DataAccessException("Testing transaction rollback"){}; //This throws an exception to test the rollback
    }

    public int getLastSpeakerId() {
        Speaker lastSpeaker = getLastSpeaker();
        if (lastSpeaker == null) {
            return 0;
        } else {
            return lastSpeaker.getId();
        }
    }

    @Override
    public void delete(int id) {
        speakerRepository.delete(id);
    }
}
