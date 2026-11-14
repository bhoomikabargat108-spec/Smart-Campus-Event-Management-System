
package dao;

import database.DBConnection;
import model.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {

    // Add Event
    public boolean addEvent(Event event) {

        String sql = "INSERT INTO events " +
                "(event_name, category, event_date, event_time, " +
                "venue, organizer, capacity, description) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, event.getEventName());
            ps.setString(2, event.getCategory());
            ps.setDate(3, Date.valueOf(event.getEventDate()));
            ps.setTime(4, Time.valueOf(event.getEventTime()));
            ps.setString(5, event.getVenue());
            ps.setString(6, event.getOrganizer());
            ps.setInt(7, event.getCapacity());
            ps.setString(8, event.getDescription());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // View All Events
    public List<Event> getAllEvents() {

        List<Event> events = new ArrayList<>();

        String sql = "SELECT * FROM events";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                Event event = new Event(
                        rs.getInt("event_id"),
                        rs.getString("event_name"),
                        rs.getString("category"),
                        rs.getDate("event_date").toLocalDate(),
                        rs.getTime("event_time") == null ? null :
                                rs.getTime("event_time").toLocalTime(),
                        rs.getString("venue"),
                        rs.getString("organizer"),
                        rs.getInt("capacity"),
                        rs.getString("description")
                );

                events.add(event);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return events;
    }

    // Update Event
    public boolean updateEvent(Event event) {

        String sql = "UPDATE events SET event_name=?, category=?, " +
                "event_date=?, event_time=?, venue=?, organizer=?, " +
                "capacity=?, description=? WHERE event_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, event.getEventName());
            ps.setString(2, event.getCategory());
            ps.setDate(3, Date.valueOf(event.getEventDate()));

            if (event.getEventTime() == null) {
                ps.setNull(4, Types.TIME);
            } else {
                ps.setTime(4, Time.valueOf(event.getEventTime()));
            }

            ps.setString(5, event.getVenue());
            ps.setString(6, event.getOrganizer());
            ps.setInt(7, event.getCapacity());
            ps.setString(8, event.getDescription());
            ps.setInt(9, event.getEventId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete Event
    public boolean deleteEvent(int eventId) {

        String sql = "DELETE FROM events WHERE event_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, eventId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
