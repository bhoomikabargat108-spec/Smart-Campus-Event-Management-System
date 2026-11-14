
package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Event {

    private int eventId;
    private String eventName;
    private String category;
    private LocalDate eventDate;
    private LocalTime eventTime;
    private String venue;
    private String organizer;
    private int capacity;
    private String description;

    // Constructor
    public Event(int eventId, String eventName, String category,
                 LocalDate eventDate, LocalTime eventTime,
                 String venue, String organizer,
                 int capacity, String description) {

        this.eventId = eventId;
        this.eventName = eventName;
        this.category = category;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.venue = venue;
        this.organizer = organizer;
        this.capacity = capacity;
        this.description = description;
    }

    // Getters and Setters

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public LocalTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalTime eventTime) {
        this.eventTime = eventTime;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
