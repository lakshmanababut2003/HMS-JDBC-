package repo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dbConfig.DBConnection;
import exceptions.CRUDFailedException;
import models.Slot;
import models.SlotStatus;
import repo.interfaces.Repo;
import repo.query.SlotQuery;

public class SlotRepo implements Repo<Slot> {

    private static SlotRepo instance;
    private final Map<Integer, Slot> slots = new HashMap<>();

    private SlotRepo() {
        
    }

    public static SlotRepo getSlotRepo() {
        if (instance == null) {
            instance = new SlotRepo();
        }
        return instance;
    }

    private Slot setSlotDetails(ResultSet rs) throws SQLException {
        return new Slot(
            rs.getInt("id"),
            rs.getInt("doctor_id"),
            rs.getDate("slot_date").toLocalDate(),
            rs.getTime("start_time").toLocalTime(),
            rs.getTime("end_time").toLocalTime(),
            SlotStatus.valueOf(rs.getString("status"))
        );
    }

    private void makePrepareStatementDetails(PreparedStatement ps, Slot slot) throws SQLException {
        ps.setInt(1, slot.getDoctorId());
        ps.setDate(2, Date.valueOf(slot.getDate()));
        ps.setTime(3, Time.valueOf(slot.getStartTime()));
        ps.setTime(4, Time.valueOf(slot.getEndTime()));
        ps.setString(5, slot.getStatus().name());
    }

    @Override
    public void loadAll() {
        try (PreparedStatement ps = DBConnection.getInstance()
                .getConnection()
                .prepareStatement(SlotQuery.LOADALL.getQuery())) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Slot slot = setSlotDetails(rs);
                slots.put(slot.getSlotId(), slot);
            }
        } catch (SQLException e) {
            System.out.println("Unable to load all slot data: ");
        }
    }

    @Override
    public Slot add(Slot slot) {
        int id = addToDB(slot);
        slot.setSlotId(id);
        slots.put(id, slot);
        return slot;
    }

    @Override
    public Slot update(Slot slot) {
        updateToDB(slot);
        slots.put(slot.getSlotId(), slot);
        return slot;
    }

    @Override
    public List<Slot> getAll() {
        return new ArrayList<>(slots.values());
    }

    @Override
    public Slot getById(int id) {
        return slots.get(id);
    }


    public List<Slot> findByDoctorAndDate(int doctorId, LocalDate date) {
        List<Slot> result = new ArrayList<>();
        for (Slot slot : slots.values()) {
            if (slot.getDoctorId() == doctorId && 
                slot.getDate().equals(date)) {
                result.add(slot);
            }
        }
        return result;
    }

    public List<Slot> findByDoctor(int doctorId) {
        List<Slot> result = new ArrayList<>();
        for (Slot slot : slots.values()) {
            if (slot.getDoctorId() == doctorId && slot.getDate().equals(LocalDate.now())) {
                result.add(slot);
            }
        }
        return result;
    }

    public List<Slot> findAvailableSlotsByDoctorAndDate(int doctorId, LocalDate date) {
        List<Slot> result = new ArrayList<>();
        for (Slot slot : slots.values()) {
            if (slot.getDoctorId() == doctorId && 
                slot.getDate().equals(date) && 
                slot.getStatus() == SlotStatus.FREE) {
                result.add(slot);
            }
        }
        return result;
    }

    public List<Slot> findByStatus(SlotStatus status) {
        List<Slot> result = new ArrayList<>();
        for (Slot slot : slots.values()) {
            if (slot.getStatus() == status) {
                result.add(slot);
            }
        }
        return result;
    }

    private int addToDB(Slot slot) {
        try (PreparedStatement ps = DBConnection.getInstance()
                .getConnection()
                .prepareStatement(SlotQuery.INSERT.getQuery(), Statement.RETURN_GENERATED_KEYS)) {

            makePrepareStatementDetails(ps, slot);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new CRUDFailedException("No ID returned after slot insertion");
            }
        } catch (SQLException e) {
            throw new CRUDFailedException("Slot insertion failed: " + e.getMessage());
        }
    }

    private void updateToDB(Slot slot) {
        try (PreparedStatement ps = DBConnection.getInstance()
                .getConnection()
                .prepareStatement(SlotQuery.UPDATE.getQuery())) {

            makePrepareStatementDetails(ps, slot);
            ps.setInt(6, slot.getSlotId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new CRUDFailedException("Slot update failed: " + e.getMessage());
        }
    }

}