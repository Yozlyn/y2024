package cn.edu.qjnu.y2024.util;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;

//代码生成工具类
public class CodeGenerator {
    public static void main(String[] args) {
//        数据库连接信息
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/y2024?serverTimezone=Asia/Shanghai", "root", "114514")
                .globalConfig(builder -> builder
                        .author("y2024")
                        .outputDir("D:/IdeaProjects/y2024/src/main/java")
                        .commentDate("yyyy-MM-dd")
                )
                .packageConfig(builder -> builder
                                .parent("cn.edu.qjnu.y2024")
//                        .entity("entity")
//                        .mapper("mapper")
//                        .service("service")
//                        .serviceImpl("service.impl")
//                        .xml("mapper.xml")
                )
                .strategyConfig(builder -> {
                            builder.addInclude("cart_items"); //设置生成的表名
                        }
//                        .entityBuilder()
//                        .enableLombok()
                )
                .execute();
    }
}