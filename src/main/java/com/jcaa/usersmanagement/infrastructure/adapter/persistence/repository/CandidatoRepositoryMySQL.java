package com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository;

import com.jcaa.usersmanagement.application.port.out.*;
import com.jcaa.usersmanagement.domain.model.CandidatoModel;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto.CandidatoPersistenceDto;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper.CandidatoPersistenceMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CandidatoRepositoryMySQL implements SaveCandidatoPort, GetAllCandidatosPort,
        GetCandidatoByIdPort, UpdateCandidatoPort, DeleteCandidatoPort {

    private final Connection connection;
    private final CandidatoPersistenceMapper mapper;

    public CandidatoRepositoryMySQL(Connection connection) {
        this.connection = connection;
        this.mapper = new CandidatoPersistenceMapper();
    }

    @Override
    public CandidatoModel save(CandidatoModel candidato) {
        String sql = "INSERT INTO candidato (id_candidato, nombre, direccion, telefono, fecha_nacimiento, fotografia, tipo, nombre_tutor) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, candidato.getId());
            stmt.setString(2, candidato.getNombre());
            stmt.setString(3, candidato.getDireccion());
            stmt.setString(4, candidato.getTelefono());
            stmt.setDate(5, candidato.getFechaNacimiento() != null ? Date.valueOf(candidato.getFechaNacimiento()) : null);
            stmt.setString(6, candidato.getFotografia());
            stmt.setString(7, candidato.getTipo().name());
            stmt.setString(8, candidato.getNombreTutor());
            stmt.executeUpdate();
            return candidato;
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar candidato: " + e.getMessage());
        }
    }

    @Override
    public List<CandidatoModel> findAll() {
        String sql = "SELECT * FROM candidato";
        List<CandidatoModel> candidatos = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                candidatos.add(mapper.toDomain(toDto(rs)));
            }
            return candidatos;
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar candidatos: " + e.getMessage());
        }
    }

    @Override
    public Optional<CandidatoModel> findById(Integer id) {
        String sql = "SELECT * FROM candidato WHERE id_candidato = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapper.toDomain(toDto(rs)));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar candidato: " + e.getMessage());
        }
    }

    @Override
    public CandidatoModel update(CandidatoModel candidato) {
        String sql = "UPDATE candidato SET nombre=?, direccion=?, telefono=?, fotografia=?, tipo=?, nombre_tutor=? WHERE id_candidato=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, candidato.getNombre());
            stmt.setString(2, candidato.getDireccion());
            stmt.setString(3, candidato.getTelefono());
            stmt.setString(4, candidato.getFotografia());
            stmt.setString(5, candidato.getTipo().name());
            stmt.setString(6, candidato.getNombreTutor());
            stmt.setInt(7, candidato.getId());
            stmt.executeUpdate();
            return candidato;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar candidato: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM candidato WHERE id_candidato = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar candidato: " + e.getMessage());
        }
    }

    private CandidatoPersistenceDto toDto(ResultSet rs) throws SQLException {
        return new CandidatoPersistenceDto(
                rs.getInt("id_candidato"),
                rs.getString("nombre"),
                rs.getString("direccion"),
                rs.getString("telefono"),
                rs.getDate("fecha_nacimiento") != null ? rs.getDate("fecha_nacimiento").toLocalDate() : null,
                rs.getString("fotografia"),
                rs.getString("tipo"),
                rs.getString("nombre_tutor")
        );
    }
}