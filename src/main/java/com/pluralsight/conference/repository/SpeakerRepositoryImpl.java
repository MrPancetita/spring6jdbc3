package com.pluralsight.conference.repository;

import com.pluralsight.conference.model.Speaker;
import com.pluralsight.conference.repository.util.SpeakerRowMapper;

import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.PreparedStatementCreator;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("speakerRepository")
public class SpeakerRepositoryImpl implements SpeakerRepository {

    private JdbcTemplate jdbcTemplate; 

    public SpeakerRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate; 
    }

    public List<Speaker> findAll() {

        //First it was with a lambda but it was moved to a SpeakerRowMapper class, so it can be reused
        List<Speaker> speakers = jdbcTemplate.query("SELECT * FROM speaker", new SpeakerRowMapper());

        return speakers;
        
    }

    @Override
    public Speaker create(Speaker speaker) {
        /* Option 1 -- Uses SQL */
        //jdbcTemplate.update("INSERT INTO speaker (name) VALUES (?)",speaker.getName());

        /* Option 1A -- Only if you care that an object is returned after its creation, a lot of extrawork 

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public java.sql.PreparedStatement createPreparedStatement(java.sql.Connection connection)
                    throws java.sql.SQLException {
                java.sql.PreparedStatement ps = connection.prepareStatement("INSERT INTO speaker (name) VALUES (?)", new String[] {"id"});
                ps.setString(1, speaker.getName());
                return ps;
            }
        }, keyHolder);

        Number id = keyHolder.getKey();

        return getSpeaker(id.intValue());
        */
         
        /* Option 2 -- More wordy but flexible (ORM approach)   */
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
        insert.setTableName("speaker");

        List<String> columns = new ArrayList<>();
        columns.add("name");

        Map<String, Object> data = new HashMap<>();
        data.put("name", speaker.getName());

        insert.setGeneratedKeyName("id");

        Number id = insert.executeAndReturnKey(data);
        return getSpeaker(id.intValue()); //This is ONLY to retrieve the object after its creation

       //return null; 
    }

    @Override
    public Speaker getSpeaker(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM speaker WHERE id = ?", new SpeakerRowMapper(), id);
    }

    @Override
    public Speaker getLastSpeaker() {
        return jdbcTemplate.queryForObject("SELECT * FROM SPEAKER ORDER BY ID DESC NULLS LAST LIMIT 1", new SpeakerRowMapper());
    }

    @Override
    public Speaker updateSpeaker(Speaker speaker) {
        
        jdbcTemplate.update("UPDATE speaker SET name = ? WHERE id = ?", speaker.getName(), speaker.getId());
        return speaker; 
    }

    @Override
    public void updateSpeaker(List<Object[]> pairs) {
        jdbcTemplate.batchUpdate("UPDATE speaker SET skill = ? WHERE id = ?", pairs);
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM speaker WHERE id = ?", id);
    }

}
