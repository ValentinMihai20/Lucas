package com.valentin.mihai.game;
import java.sql.*;
public class DataBase {

        Connection c;
        Statement stmt;
        ResultSet rs;

        public DataBase()
        {
            try
            {
                //exact ca in laborator
                Class.forName("org.sqlite.JDBC"); //incarcam driverul
                c = DriverManager.getConnection("jdbc:sqlite:DB.db"); // facem conexiunea cu baza de date.
                stmt = c.createStatement();
            }
            catch (Exception e)
            {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }



        public void updateMenuMusicVolume(float volume) throws  SQLException {
            String instruction="UPDATE SSL set MenuVolume ="+volume+" WHERE ID=1;";
            stmt.executeUpdate(instruction);
        }

        public void updateGameMusicVolume(float volume) throws  SQLException {
            String instruction="UPDATE SSL set GameVolume ="+volume+" WHERE ID=1;";
            stmt.executeUpdate(instruction);
        }


        public float getMenuVolume() throws SQLException {
            rs=stmt.executeQuery("SELECT * FROM SSL;");
            return rs.getFloat("MenuVolume");
        }


        public float getGameVolume() throws SQLException {
            rs=stmt.executeQuery("SELECT * FROM SSL;");
            return rs.getFloat("GameVolume");
        }


        public int getHeroLife() throws SQLException {
            rs=stmt.executeQuery("SELECT * FROM SSL;");
            return rs.getInt("HeroLife");
        }

    public void updateHeroLife(int heroLife) throws  SQLException {
        String instruction="UPDATE SSL set HeroLife ="+heroLife+" WHERE ID=1;";
        stmt.executeUpdate(instruction);
    }


    public int getHeroScore() throws SQLException {
            rs=stmt.executeQuery("SELECT * FROM SSL;");
            return rs.getInt("HeroScore");
        }
    public void updateHeroScore(int heroScore) throws  SQLException {
        String instruction="UPDATE SSL set HeroScore ="+heroScore+" WHERE ID=1;";
        stmt.executeUpdate(instruction);
    }


    public int getCurrentLevel() throws SQLException {
        rs=stmt.executeQuery("SELECT * FROM SSL;");
        return rs.getInt("CurrentLevel");
    }

    public void updateCurrentLevel(int currentLevel) throws  SQLException {
        String instruction="UPDATE SSL set CurrentLevel ="+currentLevel+" WHERE ID=1;";
        stmt.executeUpdate(instruction);
    }




}


