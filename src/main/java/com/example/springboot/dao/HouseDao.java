// package com.example.springboot.dao;

// import org.apache.ibatis.annotations.Param;
// import org.apache.ibatis.annotations.Select;
// import org.apache.ibatis.annotations.Update;
// import org.apache.ibatis.annotations.Insert;

// import com.example.springboot.common.BaseDao;
// import com.example.springboot.model.House;
// import java.util.List;
// import java.lang.String;


// public interface HouseDao extends BaseDao<House> {
// 	@Select("select * from t_house where name like CONCAT('%',#{val},'%') or size like CONCAT('%',#{val},'%') ")
// 	public List<House> search(@Param("val")String val);

// 	@Update("UPDATE t_house SET name=#{entity.name},size=#{entity.size} WHERE id =#{entity.id}")
// 	public int  save(@Param("entity")House entity);
	
// 	@Insert("INSERT into t_house values (#{entity.id}, #{entity.name},#{entity.size})")
// 	public int  put(@Param("entity")House entity);
	

// }
