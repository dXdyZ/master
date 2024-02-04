package org.another.springdataneo4j.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.Objects;

@Node
@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {
    @Id
    @GeneratedValue
    private Long neoId;

    private Long id;
    private float latitude, longitude;
    @JsonProperty("generationtime_ms")
    private String generationtimeMs;
    @JsonProperty("utc_offset_seconds")
    private int utcOffsetSeconds;
    private String timezone;
    @JsonProperty("timezone_abbreviation")
    private String timezoneAbbreviation;
    private float elevation;

    public Weather() {
    }

    public Weather(Long id,
                   float latitude, float longitude,
                   String generationtimeMs, int utcOffsetSeconds,
                   String timezone, String timezoneAbbreviation,
                   float elevation) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.generationtimeMs = generationtimeMs;
        this.utcOffsetSeconds = utcOffsetSeconds;
        this.timezone = timezone;
        this.timezoneAbbreviation = timezoneAbbreviation;
        this.elevation = elevation;
    }

    public Long getNeoId() {
        return neoId;
    }

    public void setNeoId(Long neoId) {
        this.neoId = neoId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getGenerationtimeMs() {
        return generationtimeMs;
    }

    public void setGenerationtimeMs(String generationtimeMs) {
        this.generationtimeMs = generationtimeMs;
    }

    public int getUtcOffsetSeconds() {
        return utcOffsetSeconds;
    }

    public void setUtcOffsetSeconds(int utcOffsetSeconds) {
        this.utcOffsetSeconds = utcOffsetSeconds;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getTimezoneAbbreviation() {
        return timezoneAbbreviation;
    }

    public void setTimezoneAbbreviation(String timezoneAbbreviation) {
        this.timezoneAbbreviation = timezoneAbbreviation;
    }

    public float getElevation() {
        return elevation;
    }

    public void setElevation(float elevation) {
        this.elevation = elevation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weather weather = (Weather) o;
        return Float.compare(latitude, weather.latitude) == 0 && Float.compare(longitude, weather.longitude) == 0 && utcOffsetSeconds == weather.utcOffsetSeconds && Float.compare(elevation, weather.elevation) == 0 && Objects.equals(neoId, weather.neoId) && Objects.equals(id, weather.id) && Objects.equals(generationtimeMs, weather.generationtimeMs) && Objects.equals(timezone, weather.timezone) && Objects.equals(timezoneAbbreviation, weather.timezoneAbbreviation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(neoId, id, latitude, longitude, generationtimeMs, utcOffsetSeconds, timezone, timezoneAbbreviation, elevation);
    }

    @Override
    public String toString() {
        return "Weather{" +
                "neoId=" + neoId +
                ", id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", generationtimeMs='" + generationtimeMs + '\'' +
                ", utcOffsetSeconds=" + utcOffsetSeconds +
                ", timezone='" + timezone + '\'' +
                ", timezoneAbbreviation='" + timezoneAbbreviation + '\'' +
                ", elevation=" + elevation +
                '}';
    }
}
