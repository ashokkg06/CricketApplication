package com.example.cricketapp.Service;

import com.example.cricketapp.DBConnection;
import com.example.cricketapp.DTO.CreateAdminRequest;
import com.example.cricketapp.DTO.CreateLoginRequest;
import com.example.cricketapp.Model.Admin;
import com.example.cricketapp.adminquery;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class AdminService {

    private PasswordEncoder passwordEncoder;

    private ModelMapper modelMapper;

    public AdminService(PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public boolean isAdminExists(String email) {
        try {
            Connection cn = DBConnection.connect();
            Statement stm = cn.createStatement();
            stm.execute("use cricketapp");
            String query = adminquery.getAdminByEmail();
            PreparedStatement statement = cn.prepareStatement(query);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Admin createAdmin(CreateAdminRequest req) {
        Admin admin = new Admin();
        if(!isAdminExists(req.getEmail())) {
            try {
                Connection cn = DBConnection.connect();
                Statement stm = cn.createStatement();
                stm.execute("use cricketapp");
                String query = adminquery.getInsertvalues();
                PreparedStatement statement = cn.prepareStatement(query);
                statement.setString(1, req.getName());
                statement.setString(2, req.getEmail());
                statement.setString(3, passwordEncoder.encode(req.getPassword()));
                statement.executeUpdate();
                admin = getAdmin(req.getEmail());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            throw new AdminAlreadyExistsException(req.getEmail());
        }
        return admin;
    }

    public Admin loginAdmin(CreateLoginRequest req) {
        Admin admin = new Admin();
        try {
            Connection cn = DBConnection.connect();
            Statement stm = cn.createStatement();
            stm.execute("use cricketapp");
            String query = adminquery.getAdminByEmail();
            PreparedStatement statement = cn.prepareStatement(query);
            statement.setString(1, req.getEmail());
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                if(passwordEncoder.matches(req.getPassword(), rs.getString("password"))) {
                    admin.setId(rs.getInt("id"));
                    admin.setName(rs.getString("name"));
                    admin.setEmail(rs.getString("email"));
                    admin.setPassword(rs.getString("password"));
                }
                else {
                    throw new InvalidCredentialsException();
                }
            }
            else {
                throw new AdminNotFoundException(req.getEmail());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }

    public Admin getAdmin(int id) {
        Admin admin = new Admin();
        try {
            Connection cn = DBConnection.connect();
            Statement stm = cn.createStatement();
            stm.execute("use cricketapp");
            String query = adminquery.getAdminById();
            PreparedStatement statement = cn.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if(rs.next())
            {
                admin.setId(rs.getInt("id"));
                admin.setName(rs.getString("name"));
                admin.setEmail(rs.getString("email"));
                admin.setPassword(rs.getString("password"));
            }
            else {
                throw new AdminNotFoundException(id);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }

    public Admin getAdmin(String email) {
        Admin admin = new Admin();
        try {
            Connection cn = DBConnection.connect();
            Statement stm = cn.createStatement();
            stm.execute("use cricketapp");
            String query = adminquery.getAdminByEmail();
            PreparedStatement statement = cn.prepareStatement(query);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if(rs.next())
            {
                admin.setId(rs.getInt("id"));
                admin.setName(rs.getString("name"));
                admin.setEmail(rs.getString("email"));
                admin.setPassword(rs.getString("password"));
            }
            else {
                throw new AdminNotFoundException(email);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }

    public static class AdminNotFoundException extends IllegalArgumentException {
        public AdminNotFoundException(String email)
        {
            super("Admin with email " + email + " Not found");
        }

        public AdminNotFoundException(int id)
        {
            super("Admin " + id + " Not Found");
        }
    }

    public static class InvalidCredentialsException extends IllegalArgumentException {
        public InvalidCredentialsException()
        {
            super("Invalid username or password combination");
        }
    }

    public static class AdminAlreadyExistsException extends IllegalArgumentException {
        public AdminAlreadyExistsException(String username) {
            super("Admin with email: " + username + " Already Exists");
        }
    }

}
