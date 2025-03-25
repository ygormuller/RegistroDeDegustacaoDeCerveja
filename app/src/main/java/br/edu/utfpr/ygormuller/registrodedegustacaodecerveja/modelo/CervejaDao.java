package br.edu.utfpr.ygormuller.registrodedegustacaodecerveja.modelo;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CervejaDao {
    @Insert
    long insert(Cerveja cerveja);

    @Update
    int update(Cerveja cerveja);

    @Delete
    int delete(Cerveja cerveja);

    @Query("SELECT * FROM cervejas WHERE id = :id")
    Cerveja queryForId(int id);

    @Query("SELECT * FROM cervejas ORDER BY nome ASC")
    List<Cerveja> queryAllAscending();

    @Query("SELECT * FROM cervejas ORDER BY nome DESC")
    List<Cerveja> queryAllDownward();
}
